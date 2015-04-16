# shard-Increment
shard table with common id increment

[how to config]
====================================================================================================
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	   http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">

    <import resource="spring-config-jndi.xml" />

    <!-- create table
        CREATE TABLE sequence_value (
            name varchar(50) NOT NULL COMMENT 'businessName',
            id int(11) NOT NULL COMMENT 'incrementId',
            PRIMARY KEY (`businessName`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='shard table increment Id';
    -->
    <bean id="incrTableConfig" class="com.plugin.id.increment.object.IncrTableConfig">
        <property name="tableName" value="tb_sequence_value" />    <!--default is tb_sequence-->
        <property name="businessId" value="id"/>                   <!--default is incrementId-->
        <property name="businessPK" value="name"/>                 <!--default is businessName-->
    </bean>

    <!--
           步长,类似缓冲区,在步长长度内的操作直接内存计算,每步长次数刷新一次数据库,
           步长太长会出现浪费ID空间,最小浪费次数1最大浪费次数步长减1.
           浪费发生在应用重启后,刷新到数据库中的数值跟内存计算的数值之间,如果期间应用停止,内存中的消失,但是缓冲区尾值被推送到数据库
           下次启动时,先走数据库如果数据库中有值,则从数据库中的值开始计算ID,如果没有则走配置的incBeginId的值,incBeginId没有配置默认为0
           incBeginId,stepWidth,incrTableConfig都可以不用配置,不配置默认值分别为0,100,sequence_value(name,id)
    -->
    <bean id="sequenceUtil" class="com.plugin.id.increment.IncrementFactory">
        <property name="dataSource" ref="plusDataSource" />
        <property name="incBeginId" value="2000"/>
        <property name="stepWidth" value="10" />
        <property name="incrTableConfig" ref="incrTableConfig" />
    </bean>

</beans>
====================================================================================================

[Test Demo]
public class SequenceTest{

    private IncrSequence sequenceUtil;

    @Before
    public void init(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config-context.xml");
        sequenceUtil = (IncrSequence) applicationContext.getBean("sequenceUtil");
        System.out.println("IS NULL：" + (sequenceUtil == null));
    }


    @Test
    public void testIinc(){
        for(int i=0 ;i<10000; i++){
            // shrardLogRuleType为业务类型描述,为用户根据需要配置的值,应用中整体不变
            long id = sequenceUtil.getIncrement("shrardLogRuleType");
            System.out.println(id);
        }
    }

    @Test
    public void testIncre() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<20; i++){
            buffer.append(executorService.submit(new MultiSequence(sequenceUtil)).get());
        }
        executorService.shutdown();
        System.out.println(buffer.toString());
    }

}

public class MultiSequence implements Callable<String> {

    private IncrSequence incrSequence;

    public MultiSequence(IncrSequence incrSequence) {
        this.incrSequence = incrSequence;
    }

    @Override
    public String call() throws Exception {
        StringBuffer buffer = new StringBuffer();
        for(int j=0 ;j<10000; j++){
            long id = incrSequence.getIncrement("abc");
            buffer.append(id).append("\r\n");
        }
        return buffer.toString();
    }

}