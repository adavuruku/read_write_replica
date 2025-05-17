package com.example.read_write_db;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.integration.gateway.AuditLogGateWay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReadWriteDbApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private AuditLogGateWay auditLogGateWay;

	@Test
	public void testSampleGateway() {
		AppSettingDto appSettingDto = new AppSettingDto();
		appSettingDto.setDescription("Hello Gate way sample test");
		appSettingDto.setGroupId(2L);
		String result = auditLogGateWay.processLog(appSettingDto);
		assertEquals("Hello Gate way sample test", result);
	}

}
