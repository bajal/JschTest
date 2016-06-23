/**
 * Created by Bajal on 6/23/16.
 */
import java.io.*;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class DemoSftp {

    public static void main(String[] args)  {

        String hostname = "localhost";
        String login = "908752";
        String password = "pass";
        String directory = "/Users/908752/outbox";

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        Session session = null;
        Channel channel = null;
        try {
            JSch ssh = new JSch();
            session = ssh.getSession(login, hostname, 22);
            session.setConfig(config);
            session.setPassword(password);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.cd(directory);

            File initialFile = new File("src/main/resources/testfiletoftp.txt");
            System.out.println(initialFile.exists());


            InputStream targetStream = new FileInputStream(initialFile);

            System.out.println("--"+sftp.pwd());

            sftp.put(targetStream, directory +"/testfiletoftp.txt");

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            channel.disconnect();
            session.disconnect();
        }



    }

}
