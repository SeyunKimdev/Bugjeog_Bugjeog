package com.bugjeogbugjeog.app.bugjeogbugjeog.service;

import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.dao.NoticeDAO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.NoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeDAO noticeDAO;

    /* 공지사항 목록 조회 */
    public List<NoticeVO> showList(){ return noticeDAO.findAll();}

    /* 공지사항  상세 보기 */
    public NoticeVO showNotice(Long noticeId){ return noticeDAO.findById(noticeId);}

    /* 공지사항  추가 */
    public void add(NoticeVO noticeVO){ noticeDAO.add(noticeVO);}

    /* 공지사항  삭제 */
    public void remove(Long noticeId){ noticeDAO.remove(noticeId);}
}
