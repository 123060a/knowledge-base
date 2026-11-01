import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 第一步：只做一件事 —— 让 Java 连上 MySQL，并读出一行数据。
 *
 * 这是“脚手架代码”，不是业务代码，可以直接用。
 * 面试不会问你怎么写这段，但它跑通的一瞬间，你就理解了 JDBC 是什么。
 *
 * 运行方式：在 IDEA 里右键 → Run 'TestConnection.main()'
 */
public class TestConnection {

    // 连接串：告诉 Java “数据库在哪、叫什么、用什么编码”
    // 拆开看：
    //   jdbc:mysql://    协议，表示走 JDBC 找 MySQL
    //   localhost:3306   地址和端口（MySQL 默认 3306）
    //   /kb              库名
    //   ? 后面是参数：编码、时区、是否用 SSL
    private static final String URL =
            "jdbc:mysql://localhost:3306/kb"
            + "?useUnicode=true&characterEncoding=utf8"
            + "&serverTimezone=Asia/Shanghai"
            + "&useSSL=false&allowPublicKeyRetrieval=true";

    private static final String USER = "root";

    // ↓↓↓ 把这一行改成你装 MySQL 时设的密码 ↓↓↓
    private static final String PASSWORD = "060321a";

    public static void main(String[] args) {
        System.out.println("正在连接数据库……");

        // try-with-resources：括号里的对象用完会自动关闭，不用手写 conn.close()
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM note")) {

            System.out.println("连接成功！");
            System.out.println("数据库信息：" + conn.getMetaData().getDatabaseProductName()
                    + " " + conn.getMetaData().getDatabaseProductVersion());

            // rs 是结果集，一开始指向第 0 行，next() 往下移一行并返回是否有数据
            if (rs.next()) {
                System.out.println("note 表里现在有 " + rs.getInt(1) + " 条笔记");
            }

        } catch (Exception e) {
            // 连不上时，把完整原因打出来，别只打 “连接失败”
            System.err.println("连接失败，原因：");
            e.printStackTrace();
        }
    }
}
