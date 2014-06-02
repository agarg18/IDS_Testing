package IDS_testing_v1;

import java.util.Scanner;

public class IDS_testing_v1 {

	public static String txtDirectory = "/home/design/Desktop/iSensorAutomation/FileIO/";
	public static String pcapDirectory = "/home/design/Desktop/iSensorAutomation/pcaps/";
	public static String rule;
	public static String testName;
	public static String pcapName;
	public static String pattern;

	public static void getInputs() {		

		Scanner scannerObject = new Scanner(System.in);

		char flag = 'n';

		while (flag!='y') {

			// Get name of pcap file
			System.out.print("Enter the name of pcap file: ");
			pcapName = scannerObject.nextLine();

			// Location of pcap file
			pcapDirectory = pcapDirectory + pcapName;

			// Get the signature for snort
			System.out.print("Enter the corresponding Snort rule: ");
			rule = scannerObject.nextLine();

			// Get the test name
			System.out.print("Enter the Test Name: ");
			testName = scannerObject.nextLine();

			// Get the pattern
			System.out.print("Enter the pattern: ");
			pattern = scannerObject.nextLine();


			//Verify Inputs
			System.out.println("pcap file: " + pcapDirectory);
			System.out.println("snort rule: " + rule);
			System.out.println("test name: " + testName);
			System.out.println("pattern: " + pattern);
			System.out.print("Verify (y/n): " );
			String inputFlag =  scannerObject.nextLine();

			flag = inputFlag.charAt(0);

		}

		scannerObject.close();
	}

	public static void updateSnort() {

		String snort_IP = "192.168.56.102";
		String snort_user = "snort-ids2";
		String snort_pass = "Prateek@123";
		String snort_ruleDirectory = "/home/snort-ids2/Desktop/";
		String snort_ruleFile = "zzzdemo.rules";

		FTP_access.fileDownload(snort_IP, snort_user, snort_pass, (snort_ruleDirectory + snort_ruleFile), (txtDirectory + snort_ruleFile));

		FileIO.appendAtEnd((txtDirectory + snort_ruleFile), rule);

		FTP_access.fileUpload(snort_IP, snort_user, snort_pass, (snort_ruleDirectory + snort_ruleFile), (txtDirectory + snort_ruleFile));

	}

	public static void updatePytbull() {
		String pytbull_IP = "192.168.56.103";
		String pytbull_user = "attacker2";
		String pytbull_pass = "Prateek@123";
		String pytbull_pcapDirectory = "/home/attacker2/Desktop/pcaps/";
		String pytbull_testDirectory = "/home/attacker2/Desktop/";
		String pytbull_testFile = "pcapReplay.py";

		FTP_access.fileUpload(pytbull_IP, pytbull_user, pytbull_pass, pytbull_pcapDirectory, pcapDirectory);

		FTP_access.fileDownload(pytbull_IP, pytbull_user, pytbull_pass, (pytbull_testDirectory + pytbull_testFile), (txtDirectory + pytbull_testFile));

		FileIO.appendTestCase(txtDirectory, pytbull_testFile, testName, pcapName, pattern);

		FTP_access.fileUpload(pytbull_IP, pytbull_user, pytbull_pass, (pytbull_testDirectory + pytbull_testFile), (txtDirectory + "modified_" + pytbull_testFile));
	}

	public static void main(String[] args) {

		/*	Get User Inputs
		 * 		1. Name of pcap file
		 * 		2. Rule corresponding to the pcap
		 */
		getInputs();

		/*	Update Snort
		 * 		1. Download rule file
		 * 		2. Append new rule
		 * 		3. Upload new rule file
		 */
		updateSnort();

		/*	Update Pytbull
		 * 		1. Uplaod pcap file
		 * 		2. Download pcap module file
		 * 		3. Add new test case
		 * 		4. Upload new pcap module file
		 */
		updatePytbull();

	}

}
