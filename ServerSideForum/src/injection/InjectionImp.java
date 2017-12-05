package injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.SumInt;

public class InjectionImp {

	public static void main(String[] args0) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		 
		SumInt hello = (SumInt) context.getBean("add");
		System.out.println(hello.display());
	}
}
