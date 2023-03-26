package com.bugjeogbugjeog.app.bugjeogbugjeog.controller;

import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.MemberVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/profile/*")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

//  --------------------- 일반 회원 조회
    @GetMapping("myinfo")
    public void memberInfo(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
//        Long memberId = (Long) session.getAttribute("memberId");
        Long memberId = 1L;

        model.addAttribute("memberVO", myPageService.memberInfo(memberId));
    }

    @PostMapping("upload-file")
    @ResponseBody
    public String memberUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
       log.info(multipartFile.toString());
        String path = "C:/upload/" + getPath();
        File file = new File(path);
        if(!file.exists()) {file.mkdirs();}

        String uuid = UUID.randomUUID().toString();
        multipartFile.transferTo(new File(path, uuid + "_" + multipartFile.getOriginalFilename()));

        if(multipartFile.getContentType().startsWith("image")){
            FileOutputStream out = new FileOutputStream(new File(path, "t_" + uuid + "_" + multipartFile.getOriginalFilename()));
            Thumbnailator.createThumbnail(multipartFile.getInputStream(), out, 400, 400);
            out.close();
        }
        return uuid;
    }

    //    파일 불러오기
    @GetMapping("display")
    @ResponseBody
    public byte[] display(String fileName) throws IOException {
        return FileCopyUtils.copyToByteArray(new File("C:/upload", fileName));
    }

    //    파일 저장
    @PostMapping("file-memeber-save")
    @ResponseBody
    public void fileSave(@RequestBody MemberVO member){
        MemberVO memberVO = myPageService.memberInfo(member.getMemberId());

        memberVO.setMemberImgOriginalName(member.getMemberImgOriginalName());
        memberVO.setMemberImgPath(member.getMemberImgPath());
        memberVO.setMemberImgUuid(member.getMemberImgUuid());
        myPageService.memberUpdate(memberVO);
    }
    
//    이름 변경
    @PostMapping("updateName")
    @ResponseBody
    public String updateName(HttpServletRequest req, @RequestParam("memberName") String memberName) {
        HttpSession session = req.getSession();
//        Long memberId = (Long) session.getAttribute("memberId");
        Long memberId = 1L;

        MemberVO memberVO = memberVO(memberId);
        memberVO.setMemberName(memberName);
        myPageService.memberUpdate(memberVO);

        return memberName;
    }

//    유저 정보 보내기
    @GetMapping("memberPhoneCheck")
    @ResponseBody
    public void memberPhoneCheck(@RequestBody Long memberId){
        MemberVO memberVO = memberVO(memberId);
    }

    // 핸드폰 인증 번호
    @PostMapping("code")
    @ResponseBody
    public String smsCode(@RequestBody String memberPhoneNumber){
        String code = myPageService.memberSMS(memberPhoneNumber);
        return code;
    };

//    핸드폰 번호 변경
    @PostMapping("phoneNumberUpdate")
    @ResponseBody
    public String phoneNumberUpdate(HttpServletRequest req, @RequestParam("memberPhoneNumber") String memberPhoneNumber){
        HttpSession session = req.getSession();
//        Long memberId = (Long) session.getAttribute("memberId");
        Long memberId = 1L;
        MemberVO memberVO = memberVO(memberId);
        memberVO.setMemberPhoneNumber(memberPhoneNumber);
        myPageService.memberUpdate(memberVO);

        return memberPhoneNumber;
    }

    //    현재 날짜 경로 구하기
    private String getPath(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    // 이용자의 정보 가져오기
    private MemberVO memberVO(Long memberId){
        return myPageService.memberInfo(memberId);
    }
}
