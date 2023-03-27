package com.bugjeogbugjeog.app.bugjeogbugjeog.domain.dao;

import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.dto.MemberInquireDTO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.dto.MemberLikeDTO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.BoardInquiryVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.BusinessVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.Criteria;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.MemberVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MypageDAO {
    private final MyPageMapper myPageMapper;

    //    회원정보 조회
    public MemberVO findById(Long memberId){
        return myPageMapper.select(memberId);
    };
    
    // 회원정보 수정
    public void updateById(MemberVO memberVO){
        myPageMapper.update(memberVO);
    };

    // 회원탈퇴
    public void deleteById(Long memberId){
        myPageMapper.deleteMember(memberId);
    };

    // 핸드폰 전체 조회
    public List<String> findAllToMemberPhoneNumber(){
        return myPageMapper.selectAllPhoneNumber();
    }

    //    유통업체 회원정보 조회
    public BusinessVO findByIdToBusiness(Long businessId){
        return myPageMapper.selectBuisness(businessId);
    };

    //    좋아요 한 게시물 수
    public Integer getCountToLike(Long memberId){
        return myPageMapper.likeCount(memberId);
    };

    //    좋아요 한 게시물 번호들
    public List<Long> findAllToLikeNumber(Long memberId){
        return myPageMapper.likeListNumber(memberId);
    };

    //    좋아요 한 게시물 리스트
    public MemberLikeDTO findAllToLike(Long boardFreeId){
        return myPageMapper.likeList(boardFreeId);
    };

    //    문의 작성 목록
    public List<BoardInquiryVO> findAllByIdToInquire(Long memberId, Criteria criteria){
        return myPageMapper.inquireList(memberId,criteria);
    };

    //    문의 게시글 작성 갯수
    public Integer getCountToInquire(Long memberId){
        return myPageMapper.inquireCount(memberId);
    };

    // 문의 답변 여부
    public Long inquireAnswer(Long boardInquireId){
        return myPageMapper.inquireAnswer(boardInquireId);
    }

}
