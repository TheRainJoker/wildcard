package it.franze.taskManager.ws.utility;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.pojo.User;

public class Utility {
	
	public static void sendMailRegistration(User user){
		try {
			java.util.Properties prop = Properties.getProperties("Properties.properties");
			java.util.Properties emailProp = new java.util.Properties();
			emailProp.put("mail.smtp.host", prop.getProperty("mail.smtp.host"));
			emailProp.put("mail.smtp.auth", prop.getProperty("mail.smtp.auth"));
			emailProp.put("mail.smtp.user", prop.getProperty("mail.smtp.user"));
			emailProp.put("mail.smtp.port", Integer.parseInt(prop.getProperty("mail.smtp.port")));		
			emailProp.put("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable"));
			Session sessione = Session.getDefaultInstance(emailProp);
			Message mm = new MimeMessage(sessione);
			mm.setSubject(prop.getProperty("mail.subject"));
			mm.setContent(String.format(prop.getProperty("mail.text"), user.getName(),user.getSurname(),encodeLinkRegistration(user)),"text/html; charset=utf-8");
			InternetAddress fromAddress = new InternetAddress(prop.getProperty("mail.from"));
		    InternetAddress toAddress = new InternetAddress(user.getEmail());
		    mm.setFrom(fromAddress);
		    mm.setRecipient(Message.RecipientType.TO, toAddress);
		    Transport transport = sessione.getTransport("smtp");
		    transport.connect("smtp.gmail.com", "franze.fabrizio@gmail.com", "Madness is like gravity");
		    transport.sendMessage(mm, mm.getAllRecipients());
		    transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean checkLinkRegistration(String link) throws SQLException, IOException{
		
		String[] result = decodeLinkRegistration(link).split("-");
		UserDAO userdao = new UserDAO();
		userdao.setId(Integer.parseInt(result[2]));
		userdao.setName(result[0]);
		userdao.setSurname(result[1]);
		return userdao.selectByQuery().size() == 0 ? false : true;		
	}

	public static String encryptMd5toString(String s){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] arr = md.digest(s.getBytes());
			return Base64.getEncoder().encodeToString(arr).replace("%", "").replace("/","").replace("?","").replace("&","").replace("$","").replace("=","").replace(":","").replace("@","").replace("#","");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encodeLinkRegistration(User user){
		String[] fieldName = encodeAccentsCharacter(user.getName(),'N').split(";");
		String[] fieldSurname = encodeAccentsCharacter(user.getSurname(),'S').split(";");
		String link = stringToHex(fieldName[0])+"-"+stringToHex(fieldSurname[0])+"-"+stringToHex(user.getId()+"")+"-"+fieldName[1]+fieldSurname[1]+";";
		return link;
		
	}
	
	public static String decodeLinkRegistration(String decode){
		String[] decodeArr = decode.split("-");
		String name = hexToString(decodeArr[0]);
		String surname = hexToString(decodeArr[1]);
		String id = hexToString(decodeArr[2]);
		
		String charName = decodeArr[3].split("S")[0].substring(1);
		String charSurname = decodeArr[3].split("S")[1].substring(0, decodeArr[3].split("S")[1].length()-1);
		
		if(charName.length()>0)
			name = decodeAccentsCharacter(name,charName);
		
		if(charSurname.length()>0)
			surname = decodeAccentsCharacter(surname,charSurname);
		return name+"-"+surname+"-"+id;
	} 
	
	public static String encodeAccentsCharacter(String string,char check){
		String character = "";
		String result ="";
		for(int i=0;i<string.length();i++)
			if((string.charAt(i) < 97 || string.charAt(i) > 122) && (string.charAt(i) < 65 || string.charAt(i) > 90) && string.charAt(i) != 39)
				character += string.charAt(i)+""+i+"_";
			else
				result += string.charAt(i);
		result += ";"+check;
		if(character!= "")
			result += character.substring(0, character.length()-1);
		return result;
	}
	
	public static String decodeAccentsCharacter(String string, String characters){
		String[] charactersArr = characters.split("_");
		char character;
		int index;
		for(String decode: charactersArr){
			character = decode.charAt(0);
			index = Integer.parseInt(decode.substring(1));
			string = string.substring(0,index)+character+string.substring(index);
		}
		return string;
	}
 
	public static String hexToString(String string){
        byte [] txtInByte = new byte [string.length() / 2];
        int j = 0;
        for (int i = 0; i < string.length(); i += 2){
                txtInByte[j++] = Byte.parseByte(string.substring(i, i + 2), 16);
        }
        return new String(txtInByte);
    }
 
	public static String stringToHex(String string){
    	char[] hex_chars = "0123456789abcdef".toCharArray();
    	byte[] buf = string.getBytes();
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i){
            chars[2 * i] = hex_chars[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = hex_chars[buf[i] & 0x0F];
        }
        return new String(chars);
    }
	
	public static String generatesToken(){
		return new BigInteger(130, new SecureRandom()).toString(32).substring(0, 10);
	}

}
