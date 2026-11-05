import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;import java.util.concurrent.locks.ReentrantLock;

/**
 * boolean byte short char int long float double
 * Boolean Byte Short Character Integer Long Float Double
 * private public protected
 * abstract class extends interface implements new static final
 * break continue return
 * switch case default
 * try catch throw throws finally
 * import package
 * true false
 * super this void
 * L F
 * == 引址基值 -> equals 底层 -- equals 逻辑比较 重写
 * IDEA --> alter enter / ctrl f12 / ctrl shift f10
 */

public class Java {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println();

        Integer intWrapper = 10; // 自动装箱
        int base = intWrapper; // 自动拆箱
        int intBase = intWrapper.intValue(); // 手动拆箱
        Integer wrapper = Integer.valueOf(10); // 手动装箱 优先走缓存池
        int num = Integer.parseInt("10");

        String str = "HelloJava"; // new 新建对象再复用
        char[] charArray = str.toCharArray();

        StringBuilder sb = new StringBuilder("");
        int len = sb.length();

        String s = sb.toString();

        // Math

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        int[] arr = new int[10];
        int[] arrS = {1, 2, 3, 4, 5};

        /**
         * 容器
         * Collection
         *   List -> LinkedList / ArrayList
         *   Set -> TreeSet / HashSet / LinkedHashSet
         * Map
         *   TreeMap / LinkedHashMap / HashMap
         */
        Collection collectionArray = new ArrayList();
        Collection collectionLinked = new LinkedList();

        List arrayList = new ArrayList();
        List linkedList = new LinkedList();

        Set set = new HashSet(); // 纯无序
        Set setLinked = new LinkedHashSet(); // 维护迭代顺序 -- 双链表

        /* Stream */
        // 实现java.util.Collection接口的集合均支持stream()方法 --> 进而支持map()、filter()方法 --> 支持自己写返回值
        // 尾部配合toList() 以及 toSet() 收集结果为目标类型

        Iterator iter = collectionArray.iterator();
        while (iter.hasNext()) {
            Object item = iter.next();
        }

        ListIterator listIter = arrayList.listIterator();
        while (listIter.hasNext() && listIter.hasPrevious()) {
            Object nextItem = listIter.next();
            Object previousItem = listIter.previous();
        }

        Map<String, Object> mapHash = new HashMap<>(); // 纯无序
        Map<String, Object> mapLinkedHash = new LinkedHashMap<>();

        // () -> {} lambda表达式
        // collection.forEach(ClassName::function); 方法引用

        // 增强 for 对于引用类型会改动内部 / 常用于 Collection / Array
        for (Object item : collectionArray) {
        }

        File file = new File("");

        // --> IO 流 (字符流/字节流 -- 输入流/输出流)
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("path"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("path", true));

        // --> Buffered 封装源流, 提供缓冲区, 提高效率 减少系统从用户态到内核态的调用次数 缓冲区属于JVM堆内存[用户态] --> 默认1024*8字节即1KB

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt"), StandardCharsets.UTF_8));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.txt", true), StandardCharsets.UTF_8));

        /* byte[] buf = new byte[1024] / read(buf) / "str".getBytes(StandardCharsets.UTF_8) / write(data) / close / flush */

        /* cn.hutool.core.io.IoUtil */
        // --> 读取
        // List<String> lines = IoUtil.readLines(in, "UTF-8", new ArrayList<>()); // 读取全部行放到集合
        // String content = IoUtil.readUtf8(in); // 适合读取小文件, 一次性读取全部内容为字符串
        // byte[] bytes = IoUtil.readBytes(in); // 读取字节数组, 适合图片等二进制文件
        // --> 写入
        // IoUtil.write(content, false, out, StandardCharsets.UTF_8); // 内容, 是否追加, 输出流, 编码
        // IoUtil.writeUtf8(content, false, out);
        // IoUtil.copy(in, out); // 拷贝流 -- 字节层面
        // IoUtil.close(in); // 关闭流


        /* 多进程
            进程: 系统资源分配的基本单位 -> 独立性、动态性、并发性
        */
        ProcessBuilder pb = new ProcessBuilder("python3", "test.py");
        Process p = pb.start(); // 向系统发起请求, 创建子进程 --> 子进程 和 JVM 是 隔离的两块进程
        long pid = p.pid(); // 获取进程pid
        boolean alive = p.isAlive(); // 判断进程是否还活着
        int code = p.waitFor(); // 当前主进程卡住, 等待子进程结束返回退出码才继续 -- 0表示正常执行成功
        boolean finished = p.waitFor(10, TimeUnit.SECONDS); // 最多等待10s, 返回 true 表示10s内进程自己结束, 返回 false 表示超时进程还活着函数直接返回
        p.destroy(); // 温和关闭子进程
        p.destroyForcibly(); // 强制杀死子进程
        long jvmPid = ProcessHandle.current().pid();
        pb.directory(new File("/home/user")); // 设置子进程工作目录, 即进程执行时, cd xxx
        pb.inheritIO(); // 将子进程输入输出绑定到 JVM 关联控制台
    }
}


/* 多线程
            并行: 同一时刻, 有多个指令在多个CPU上同时执行
            并发: 同一时刻, 有多个指令在单个或多个CPU上交替执行
            线程: 进程可以同时执行多个任务, 每个任务就是线程, 线程是CPU调度的基本单位
        */

/* 开启线程方式一: 传统 */
// 并行/并发由操作系统调控
class ThreadDemo {
    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i <= 99; i++) {
            System.out.println("线程任务执行" + i);
        }
    }
}

/* 开启线程方式二: 无返回值 */
class ThreadRunnable {
    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();

        /* 共用同一个Runnable实例, 容易发生线程安全竞争 */
        Thread thread = new Thread(mr, "threadName");
        Thread otherThread = new Thread(mr, "threadName");

        thread.start();
        otherThread.start();
    }
}

// 实现接口方式, 还可以继承其他类
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i <= 99; i++) {
            System.out.println("自己的线程任务" + i);
        }
    }
}

/* 开启线程方式三: 带返回值 */
class ThreadCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable mc = new MyCallable();

        /* 共用同一个Callable实例, 容易发生线程安全竞争 */
        FutureTask<Integer> task = new FutureTask<>(mc);
        FutureTask<Integer> otherTask = new FutureTask<>(mc);

        Thread thread = new Thread(task, "threadName");
        Thread otherThread = new Thread(otherTask, "threadName");

        thread.start();
        otherThread.start();

        Integer result = task.get(); // 阻塞方法, 会发生阻塞
        System.out.println("task 返回的结果为:" + result);

        Integer otherResult = otherTask.get();
        System.out.println("otherTask 返回的结果为:" + otherResult);
    }
}

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 99; i++) {
            sum += i;
        }

        return sum;
    }
}

//thread.getName();
//thread.setName("threadName");
//static Thread currentThread();
//static void sleep(long time); // 单位 ms
//如果直接写 thread.run(): 只是普通方法调用, 不会开启新线程, 在主线程串行执行

class ThreadVariable {
    /* 线程局部变量 */
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    // threadLocal.set("value");
    String value = threadLocal.get();
    // threadLocal.remove();

    // 线程局部变量是独立的
    Thread thread = new Thread(() -> {
        threadLocal.set("value");
        getData("thread");
    }, "thread");

    private static void getData(String threadName) {
        Object data = threadLocal.get();
        System.out.println(threadName + "-" + data);
    }
}


/* 线程优先级 */
// 线程调度方式有两种: 抢占式调度和非抢占式调度, 抢占式调度具有随机性, 非抢占式调度则是有顺序的执行, Java中使用的是抢占式调度风格, 即允许随机性执行
// 线程优先级越高, 抢到CPU的概率越大 优先级: 1-10 (默认5, 10最大)

//public setPriority(int newPriority);
//public final int getPriority();

// 设置守护线程
//thread.setDaemon(true);

/* 线程安全问题 */
//--> 资源默认共用 --> 由于并行/并发机制, 代码分线程分行块被执行而产生对齐问题 --> 需要锁机制
// 锁对象可以是任何对象, 但是需要保证多条线程的锁对象是同一把锁
// 注意: 利用锁实现同步可以解决对齐问题/数据安全问题, 但是也会降低程序运行的效率
class TicketTask1 implements Runnable {
    int tickets = 1;

    @Override
    public void run() {
        while (true) {
            // 同步代码块
            synchronized (TicketTask1.class) {
                if (tickets == 0) {
                    break;
                }

                System.out.println(Thread.currentThread().getName() + "卖出第" + tickets + "张票");
                tickets--;
            }
        }
    }
}

// 同步方法 --> 静态方法(锁对象是类字节码对象) 与 非静态方法(this对象)
//static synchronized void method() {}
//private synchronized void method() {}

/* Lock锁: 更灵活 */
class ThreadTest {
    public static void main(String[] args) {
        TicketTask1 task = new TicketTask1();

        Thread thread = new Thread(task, "窗口A");
        Thread otherThread = new Thread(task, "窗口B");

        thread.start();
        otherThread.start();
    }
}

class TicketTask2 implements Runnable {
    int tickets = 1;
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                if (tickets == 0) {
                    break;
                }

                System.out.println(Thread.currentThread().getName() + "卖出第" + tickets + "张票");
                tickets--;
            } finally {
                lock.unlock();
            }
        }
    }
}


/* 线程池 */
//--> 减少线程的创建和销毁的开销
// JDK自带线程池, 了解 --> 开发通常无法做到精细配置, 不咋用
//// 自定义线程池
//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
//7个核心参数:
//        1.核心线程数量 (正式员工)
//2.最大线程数量 (正式员工 + 临时工)
//3.空闲时间
//4.时间单位
//5.任务队列 (指定排队人数)
//6.线程对象工厂
//7.拒绝策略

class ThreadPoolDemo {
    /*
        自定义线程池:
        1.有界队列 new ArrayBlockingQueue<>(N)
        2.无界队列 new LinkedBlockingQueue<>()
    */
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2, 5, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        for (int i = 1; i <= 16; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "--- 提交了任务");
                }
            });
        }
    }
}

//拒绝策略:
//ThreadPoolExecutor.AbortPolicy 丢弃任务并抛出RejectedExecutionException异常 (默认)
//        ThreadPoolExecutor.DiscardPolicy 丢弃任务, 但是不抛出异常, 这是不推荐的做法
//        ThreadPoolExecutor.DiscardOldestPolicy 抛弃队列中等待最久的任务, 然后把当前任务加入队列中
//ThreadPoolExecutor.CallerRunsPolicy 调用任务的run()方法, 绕过线程池直接执行
//临时线程什么时候创建?
//线程任务数 > 核心线程数 + 任务队列的数量
//什么时候会开启拒绝策略?
//线程任务数 > 最大线程数 + 任务队列的数量


enum ErrorCode {
    // 一行就是一条预设好的记录
    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    // 三个“格子”，用来存上面每一行的3个信息
    private final int code;
    private final String message;
    private final String description;

    // 构造：就是把括号里的三个值，放进上面三个格子里
    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}


///* 类加载器 */
//--> 负责将类的字节码加载到方法区
//加载时机: 用到的时候加载
//加载过程: 加载 -> 链接 -> 初始化

//1.加载: 通过包名 + 类名, 获取这个类, 准备用流进行传输 --> 将类加载到内存 --> 加载完毕创建一个class对象
//2.链接: 验证类是否符合 JVM 规范, 安全性检查 --> 准备 (未static变量分配空间, 设置默认值 -- 静态区) --> 解析
//3.初始化: 根据程序员编码制定的主观计划去初始化类变量和其他资源

//类加载器的分类 -- 存在父子关系(上下级关系) -- 非代码层面 extends 而是 逻辑层面 关系:
//Bootstrap class loader: 启动类加载器, 虚拟机的内置类加载器, 通常表示为null --> 负责加载 JDK 自带类
//Platform class loader: 平台类加载器, 负责JDK中一些特殊模块 --> 负责加载 lin/modules 内部的类
//  -> JDK9 之前是 Extension Class Loader 扩展类加载器 --> 负责加载 jre/lib/ext 内部的类
//Application class loader: 应用程序类加载器
//自定义类加载器: 上级为 Application

//// 获取类加载器对象
//public Classloader getClassLoader(); // 获取该类的类加载器对象
//--> xxx.class.getClassLoader();

//双亲委派模式
//如果一个类加载器收到了类加载请求，它并不会自己先去加载
//        而是把这个请求委托给父类的加载器去执行
//如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归
//        请求最终将到达顶层的启动类加载器
//如果父类加载器可以完成类加载任务，就成功返回
//倘若父类加载器无法完成此加载任务，子加载器才会尝试自己去加载，这就是双亲委派模式
//BootstrapClassLoader（启动类加载器，C写，拿不到引用 → null）
//        ↓ parent
//PlatformClassLoader（平台加载器，以前叫扩展加载器）
//        ↓ parent
//SystemClassLoader（就是 getSystemClassLoader() 返回的这个）

//// --> java支持内部类, 其中有个特别形式内部类 --> 匿名内部类(临时重写类内部逻辑创建实例)
//Runnable runnable = new Runnable() {
//    @Override
//    public void run() {
//        System.out.println("执行任务");
//    }
//}


///* 反射: 框架技术的灵魂 */
//// 在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法，对于任意一个对象，都能够调用它的任意属性和方法，这种动态获取信息以及动态调用对象方法的功能称为Java语言的反射机制
//// 对于开发人员自己写的代码自己知道, 但是对于框架来说框架本身是不知道的, 框架要实现自己特定的语法功能然后需要调用关联开发者写的类代码里面的属性或者功能, 则需要使用反射来操作
//// 获取字节码对象
//1.Class.forName("全类名"); // 全类名 = 包.类名
//2.类名.class
//3.对象.getClass();

//--> 类字节码对象.getClassLoader().getResourceAsStream("file.txt"); // target/classes 即 classpath 作为根路径

//Object obj = new Object();
//Class clazz = obj.getClass();

//// Class clazz = Class.forName("PACKAGE.CLASSNAME");

//Field[] fields = clazz.getDeclaredFields(); // 获取所有成员变量
//Field publicFields = clazz.getFields(); // 只获取公有成员变量
//for (Field field : fields) {
//    System.out.println(field.getName()); // 获取成员变量名
//    System.out.println(field.getType()); // 变量类型 (字节码对象)
//}

//Method[] methods = clazz.getDeclaredMethods(); // 获取所有方法
//Method publicMethods = clazz.getMethods(); // 只获取公有方法

//Parameter[] parameters = method.getParameters(); // JDK8+ 支持

//for (Method method : methods) {
//    System.out.println(method.getName()); // 获取方法名
//    System.out.println(method.getReturnType()); // 返回值类型 (字节码对象)
//}

//for (Parameter parameter : parameters) {
//    String parameterName = parameter.getName();
//    Class parameterType = parameter.getType();
//    System.out.println(parameterName + ": " + parameterType);
//}

//// 调用方法
//if ("func".equals(method.getName())) {
//    // 若调用私有方法需要添加这句赋予权限
//    // method.setAccessible(true);
//    method.invoke(clazz.newInstance(), args);
//}


///* 普通/静态代理 */
//public interface Sale {
//
//    void sale(double money);
//
//    void hire(double money);
//}

//public class Owner implements Sale {
//
//    public void sale(double money) {
//        System.out.println("出售商品, 获得" + money);
//    }
//
//    public void hire(double money) {
//        System.out.println("雇佣员工, 获得" + money);
//    }
//}

//public class SaleImpl implements Sale {
//
//    private Owner owner;
//
//    public SaleImpl(Owner owner) {
//        this.owner = owner;
//    }
//
//    @Override
//    public void sale(double money) {
//        money += 20000;
//        owner.sale(money);
//    }
//
//    @Override
//    public void hire(double money) {
//        money -= 10000;
//        owner.hire(money);
//    }
//}

///* 动态代理 */
//// 业务接口：规定功能
//public interface UserService {
//    // 添加用户
//    void addUser(String name);
//    // 删除用户
//    void deleteUser(int id);
//}

//// 原始目标类，实现上面的接口，这就是被代理对象
//public class UserServiceImpl implements UserService{
//    @Override
//    public void addUser(String name) {
//        // 原生原有逻辑，没有任何增强
//        System.out.println("【原生逻辑】添加用户：" + name);
//    }
//
//    @Override
//    public void deleteUser(int id) {
//        System.out.println("【原生逻辑】删除用户，id=" + id);
//    }
//}

//public class ProxyDemo {
//    public static void main(String[] args) {
//        // 1. 创建【原始目标对象】，真正干活的对象
//        UserService target = new UserServiceImpl();
//
//        // 2. 生成代理对象
//        UserService proxyUserService = (UserService) Proxy.newProxyInstance(
//                // 参数1：类加载器，用来加载自动生成的代理类
//                ClassLoader.getSystemClassLoader(),
//                // 参数2：接口数组，代理类要实现哪些接口（就是我们自己写的UserService）
//                new Class[]{UserService.class},
//                // 参数3：InvocationHandler，所有方法调用都会进到invoke里面
//                new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        // proxy：代理对象本身，一般不用
//                        // method：当前被调用的方法（反射对象）
//                        // args：调用方法传进来的参数
//
//                        // 获取当前调用的方法名字
//                        String methodName = method.getName();
//
//                        // 判断：如果是addUser方法，我们给它加增强（统计耗时）
//                        if ("addUser".equals(methodName)) {
//                            // ========== 前置增强代码（原方法执行之前跑） ==========
//                            long start = System.currentTimeMillis();
//                            System.out.println("===== 开始执行addUser =====");
//
//                            // ========== 核心：调用原始目标对象的原生方法，执行原有逻辑 ==========
//                            Object result = method.invoke(target, args);
//
//                            // ========== 后置增强代码（原方法跑完之后跑） ==========
//                            long end = System.currentTimeMillis();
//                            System.out.println("===== addUser执行结束，耗时：" + (end - start) + "ms =====");
//
//                            // 把原生方法返回值返回
//                            return result;
//                        }
//
//                        // 不是addUser的方法（deleteUser），不增强，直接执行原生逻辑
//                        return method.invoke(target, args);
//                    }
//                }
//        );
//
//        // ========== 使用代理对象调用方法！！重点：用代理对象调用，才会触发增强 ==========
//        proxyUserService.addUser("张三");
//        System.out.println("------------------------");
//        proxyUserService.deleteUser(1001);
//    }
//}

///* 注解 -- Annotation */
//基础常见注解:
//@Override
//@Deprecated
//@SuppressWarnings("all")

//自定义注解:
//@Target(ElementType.METHOD) // TYPE FIELD METHOD PARAMETER CONSTRUCTOR LOCAL_VARIABLE
//@Retention(RetentionPolicy.RUNTIME) // SOURCE CLASS RUNTIME
//public @interface MyAnnotation {

//    public String attr() default "defaultValue";
//}

//AOP 逻辑增强:
//@Aspect
//@Component
//public class MyAnnotationAspect {
//
//    @Pointcut("@annotation(com.example.project.annotation.MyAnnotation)")
//    public void pointcut() {}
//
//    @Around("pointcut()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        /* 准备工作 */
//        // 获取方法签名
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//
//        // 获取方法注解对象
//        MyAnnotation annotation = signature.getMethod().getAnnotation(MyAnnotation.class);
//
//        // 读取注解属性
//        String attr = annotation.attr();
//
//        /* 前置逻辑增强 */
//        long start = System.currentTimeMillis();
//        System.out.println("开始执行方法：" + signature.getName() + "，属性：" + attr);
//
//        // 执行目标方法
//        Object result = joinPoint.proceed();
//
//        long cost = System.currentTimeMillis() - start;
//        System.out.println("方法执行结束：" + signature.getName() + "，耗时：" + cost + "ms");
//
//        return result;
//    }
//}

