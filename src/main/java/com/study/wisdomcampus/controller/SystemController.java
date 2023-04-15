package com.study.wisdomcampus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.study.wisdomcampus.entity.Admin;
import com.study.wisdomcampus.entity.LoginFrom;
import com.study.wisdomcampus.entity.Student;
import com.study.wisdomcampus.entity.Teacher;
import com.study.wisdomcampus.service.IAdminService;
import com.study.wisdomcampus.service.IStudentService;
import com.study.wisdomcampus.service.ITeacherService;
import com.study.wisdomcampus.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {


    final
    IAdminService adminService;

    final
    IStudentService studentService;

    final
    ITeacherService teacherService;

    public SystemController(IAdminService adminService, IStudentService service, ITeacherService teacherService) {
        this.adminService = adminService;
        this.studentService = service;
        this.teacherService = teacherService;
    }


    /**
     * 修改登录用户密码
     *
     * @param newPwd 新密码
     * @param oldPwd 旧密码
     * @param token  token
     * @return 执行结果
     */
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@PathVariable("newPwd") String newPwd,
                            @PathVariable("oldPwd") String oldPwd,
                            @RequestHeader("token") String token) {

        if (JwtHelper.isExpiration(token)) {
            return Result.fail().message("token失效,请重新登录");
        }
        //获取用户id 用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        if (userId != null && userType != null) {

            //将明文密码进行加密
            oldPwd = MD5.encrypt(oldPwd);
            newPwd = MD5.encrypt(newPwd);

            switch (userType) {
                case 1:
                    Admin admin = adminService.getOne(
                            new LambdaQueryWrapper<Admin>()
                                    .eq(Admin::getId, userId)
                                    .eq(Admin::getPassword, oldPwd));
                    if (admin == null) {
                        return Result.fail().message("原密码有误！");
                    }
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                    break;
                case 2:
                    Student student = studentService.getOne(
                            new LambdaQueryWrapper<Student>()
                                    .eq(Student::getId, userId)
                                    .eq(Student::getPassword, oldPwd));
                    if (student == null) {
                        return Result.fail().message("原密码有误！");
                    }
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                    break;
                case 3:
                    Teacher teacher = teacherService.getOne(
                            new LambdaQueryWrapper<Teacher>()
                                    .eq(Teacher::getId, userId)
                                    .eq(Teacher::getPassword, oldPwd));
                    if (teacher == null) {
                        return Result.fail().message("原密码有误！");
                    }
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                    break;
            }

            return Result.ok();

        }

        return Result.fail().message("用户id或用户类型为空");

    }


    /**
     * 头像文件上传
     *
     * @param multipartFile 头像文件
     * @return 头像保存路径
     */
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) {

        //生成新文件名，防止重名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i;
        String newFileName = null;
        if (originalFilename != null) {
            i = originalFilename.lastIndexOf(".");
            newFileName = uuid.concat(originalFilename.substring(i));

            //保存文件
            if (!multipartFile.isEmpty()) {
                //保存到文件服务器，OSS服务器
                try {
                    multipartFile.transferTo(
                            new File("E:\\JavaStudy\\SpringBoot\\wisdomcampus\\target\\classes\\public\\upload\\".concat(newFileName)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //相应图片路径
        String path;
        if (newFileName != null) {
            path = "upload/".concat(newFileName);
            return Result.ok(path);
        }
        return Result.fail();
    }


    /**
     * 获取登录用户信息
     *
     * @param token token
     * @return Result
     */
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader String token) {
        //判断当前token是否过期
        if (JwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //解析token中的用户信息
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        //准备一个Map存放相应数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getById(userId);
                map.put("userType", 1);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getById(userId);
                map.put("userType", 2);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
                break;
        }
        return Result.ok(map);
    }


    /**
     * 登录验证
     *
     * @param loginFrom 登录表单
     * @param request
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFrom loginFrom, HttpServletRequest request) {
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginFrom.getVerifiCode();

        if (sessionVerifiCode == null || sessionVerifiCode.isEmpty()) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)) {
            return Result.fail().message("验证码有误，请重新输入");
        }
        //从session域中移除验证码
        session.removeAttribute("verifiCode");

        //分用户类型进行校验

        //准备一个Map存放相应数据
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            switch (loginFrom.getUserType()) {
                case 1:
                    Admin admin = adminService.login(loginFrom);
                    if (null != admin) {
                        //将用户类型和用户id转换成密文，以token的形势向客户端返回
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    break;
                case 2:
                    Student student = studentService.login(loginFrom);
                    if (null != student) {
                        //将用户类型和用户id转换成密文，以token的形势向客户端返回
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    break;
                case 3:
                    Teacher teacher = teacherService.login(loginFrom);
                    if (null != teacher) {
                        //将用户类型和用户id转换成密文，以token的形势向客户端返回
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    break;
            }
            return map.isEmpty() ? Result.fail().message("无法找到该用户") : Result.ok(map);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Result.fail().message(e.getMessage());
        }
    }

    /**
     * 生成前端验证码图片
     *
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        // 获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        // 将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);
        // 将验证码响应给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

