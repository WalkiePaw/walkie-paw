package com.WalkiePaw.domain.chatroom.service;

import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ChatroomServiceTest {

    @Autowired
    private ChatroomRepository chatroomRepository;
    @PersistenceContext
    EntityManager em;

//    @Test
//    void 채팅방_리스트_테스트() {
//        Member member1 = Member.builder().build();
//        Member member2 = Member.builder().build();
//        em.persist(member1);
//        em.persist(member2);
//
//        Board board = Board.builder().member(member1).build();
//        em.persist(board);
//
//        Chatroom chatroom = new Chatroom(board, member2);
//        em.persist(chatroom);
//
//        System.out.println("chatroom.getModifiedDate() = " + chatroom.getModifiedDate());
//        em.flush();
//        em.clear();
//
//        List<ChatroomListResponse> byMemberId = chatroomRepository.findByMemberId(member1.getId());
//
//        System.out.println("byMemberId = " + byMemberId);
//    }
}