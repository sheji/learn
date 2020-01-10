package com.gupaoedu.vip.pattern.proxy.dynamicproxy.gpproxy;
import com.gupaoedu.vip.pattern.proxy.Person;
import java.lang.reflect.*;
public class $Proxy0 implements com.gupaoedu.vip.pattern.proxy.Person{
GPInvocationHandler h;
public $Proxy0(GPInvocationHandler h) { 
this.h = h;}
public void findLove() {
try{
Method m = com.gupaoedu.vip.pattern.proxy.Person.class.getMethod("findLove",new Class[]{});
this.h.invoke(this,m,new Object[]{});
}catch(Error _ex) { }catch(Throwable e){
throw new UndeclaredThrowableException(e);
}}}
