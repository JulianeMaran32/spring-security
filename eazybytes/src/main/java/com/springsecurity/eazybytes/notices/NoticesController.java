package com.springsecurity.eazybytes.notices;

import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@AllArgsConstructor
public class NoticesController {

	private final NoticeRepository noticeRepository;

	@GetMapping("/notices")
	public ResponseEntity<Optional<Notice>> getNotices() {
		Optional<Notice> notices = noticeRepository.findAllActiveNotices();
		if (notices != null) {
			return ResponseEntity.ok()
					.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
					.body(notices);
		} else {
			return null;
		}
	}

}
