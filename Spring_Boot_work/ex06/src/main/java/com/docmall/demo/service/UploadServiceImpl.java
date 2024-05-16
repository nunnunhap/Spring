package com.docmall.demo.service;

import org.springframework.stereotype.Service;

import com.docmall.demo.controller.UploadController;
import com.docmall.demo.mapper.UploadMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService{
	
	// 주입작업
	private final UploadMapper uploadMapper;
	
	
}
