package IDS_testing_v1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


public class FTP_access {

	public static void fileDownload(String server_ip, String username, String password, String server_directory, String host_directory) {
		
		int port = 21;

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server_ip, port);
			ftpClient.login(username, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


			String remoteFile1 = server_directory;
			File downloadFile1 = new File(host_directory);
			OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
			boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
			outputStream1.close();

			if (success) {
				System.out.println("File has been transfered successfully.");
			}
			else {
				System.out.println("File transfer failed.");
			}

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void fileUpload(String server_ip, String username, String password, String server_directory, String host_directory) {
		
		int port = 21;

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server_ip, port);
			ftpClient.login(username, password);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File firstLocalFile = new File(host_directory);

			String firstRemoteFile = server_directory;
			InputStream inputStream = new FileInputStream(firstLocalFile);

			System.out.println("Start uploading first file");
			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			inputStream.close();
			if (done) {
				System.out.println("The first file is uploaded successfully.");
			} else {
				System.out.println("File upload failed.");
			}

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
