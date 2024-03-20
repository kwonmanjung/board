package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public Board write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath ,fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        return boardRepository.save(board);

    }


    // 글 작성 처리
    public Board write(Board board){

       return boardRepository.save(board);
    }

    // 글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }

    // 글 리스트 처리 2
//    public List<Board> boardList(Pageable pageable) {
//
//        return boardRepository.findAll();
//    }

    public Page<Board>  boardSearchList(String searchKeyword, Pageable pageable){
      return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 글 상세보기 처리
    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    // 글 삭제 처리
    public void boardDelete(Integer id ){
        boardRepository.deleteById(id);
    }



}
