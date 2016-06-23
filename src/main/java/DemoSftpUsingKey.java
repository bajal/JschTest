/**
 * Created by Bajal on 6/23/16.
 */

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DemoSftpUsingKey {

    public static void main(String[] args)  {

        String hostname = "localhost";
        String login = "908752";
        String directory = "/Users/908752/outbox";
        String privateKey = "/Users/908752/.ssh/id_rsa";
        int port = 22;

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        Session session = null;
        Channel channel = null;
        try {

            JSch ssh = new JSch();
            session = ssh.getSession(login, hostname, port);

            ssh.addIdentity(privateKey);
            System.out.println("identity added ");

            session.setConfig(config);

            session.connect();
            System.out.println("session connected.....");

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
