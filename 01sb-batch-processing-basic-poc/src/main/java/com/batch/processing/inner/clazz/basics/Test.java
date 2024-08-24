package com.batch.processing.inner.clazz.basics;

public class Test {
	
	public void m1() {
		System.out.println("inside m1() method");
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		t.m1();
		System.out.println("t :: super class name - " + t.getClass().getSuperclass());
		
		Test t1 = new Test() {};
		t1.m1();
		System.out.println("t1 :: super class name - " + t1.getClass().getSuperclass());
		
		Test t2 = new Test() {{m1();}};
		System.out.println("t2 :: super class name - " + t2.getClass().getSuperclass());
	}
}
