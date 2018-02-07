package com.cg.cm.organize;

import com.demo.cm.utils.ApplicationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;

@SpringBootApplication
public class OrganizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizeApplication.class, args);
		//获取class名称
		String clazzName = new Object() {
			public String getClassName()
			{
				String clazzName = this.getClass().getName();
				return clazzName.substring(0, clazzName.lastIndexOf('$'));
			}
		}.getClassName();
		//写pid文件
		ApplicationUtils.writePID(clazzName.substring(clazzName.lastIndexOf(".")+1,clazzName.length()));
	}
}
