package com.tradeshft.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tradeshft.demo.entity.NodeEntity;
import com.tradeshft.demo.repository.NodeRepository;
import com.tradeshft.demo.service.NodeService;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
	@Autowired
	private NodeRepository repository;

	public static void main(String[] args) {
		logger.info(" Demo Application Starting...");
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Demo Application populating data in H2 db...");
		/*
		NodeEntity root =new NodeEntity("root","null","null",0);
		NodeEntity a =(new NodeEntity("a","root","root",1));
		NodeEntity b =(new NodeEntity("b","root","root",1));
		NodeEntity c =(new NodeEntity("c","a","root",2));
		NodeEntity d =(new NodeEntity("d","a","root",2));
		(root.children).add(a);
		(root.children).add(b);
		((root.children).get(0).children).add(c);
		((root.children).get(0).children).add(d);
		 //root.children.add(d);
		repository.save(root);*/
		repository.save(new NodeEntity("root","null","null",0));
		repository.save(new NodeEntity("a","root","root",1));
		repository.save(new NodeEntity("b","root","root",1));
		repository.save(new NodeEntity("c","a","root",2));
		repository.save(new NodeEntity("d","a","root",2));
		repository.save(new NodeEntity("j","a","root",2));
		repository.save(new NodeEntity("e","b","root",2));
		repository.save(new NodeEntity("f","b","root",2));
		repository.save(new NodeEntity("k","b","root",2));
		
		repository.save(new NodeEntity("g","f","root",3));
		repository.save(new NodeEntity("m","f","root",3));
		repository.save(new NodeEntity("o","c","root",3));
		repository.save(new NodeEntity("p","c","root",3));
		repository.save(new NodeEntity("h","g","root",4));
		repository.save(new NodeEntity("i","g","root",4));
		repository.save(new NodeEntity("l","g","root",4));
		
		repository.save(new NodeEntity("x","m","root",4));
		repository.save(new NodeEntity("y","m","root",4));
		repository.save(new NodeEntity("v","o","root",4));
		repository.save(new NodeEntity("w","o","root",4));

       /* System.out.println("\nfindAll()");
        repository.findAll().forEach(i -> System.out.println(i));*/

		
	}
	
	
}

