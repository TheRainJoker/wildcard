package it.franze.taskManager.ws.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import it.franze.taskManager.ws.dao.AccountDAO;
import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.pojo.Account;
import it.franze.taskManager.ws.pojo.User;
import it.franze.taskManager.ws.utility.Utility;

public class test {

	public static void main(String[] args) throws Exception {
//		String pass = "F4br1z10";
//		
//		
//		UserDAO userdao = new UserDAO(1,"Fabrizio","Franzè","franze.fabrizio@gmail.com","3455028163","franze.fabrizio@gmail.com",Utility.encryptMd5toString(pass),'Y');
//		userdao.update();
//		String format = "<p>Complimenti %s %s, la tua registrazione a Task Manager è andata a buon fine!</p><br/><p>Clicca sul link sotto per attivare il tuo account:</p><br/><a href='http://localhost:8080/TaskManagerWS/user/active/%s'>Click here to active account!</a><br/><br/><p>Se non hai eseguito nessuna procedura di registrazione per il nostro sito ti preghiamo di cancellare l'email.</p><br/><br/><br/><p>Buona giornata!</p>";
//		String s = String.format(format, "Fabrizio","Franzè","Link");
//		System.out.println(s);
		

		
		String name = "Fabrizio";
		String[] formatField = encodeAccentsCharacter(name,'N').split(";");
		name = formatField[0];
		String charName = formatField[1];
		
		System.out.println("Name: "+ name+"\nCharacter: "+charName);
		System.out.println();
		
		
		String surname = "Franze";
		formatField = encodeAccentsCharacter(surname,'S').split(";");
		surname = formatField[0];
		String charSurname = formatField[1];
		
		System.out.println("Surname: "+ surname+"\nCharacter: "+charSurname);
		System.out.println();
		
		String id = "1";
		
		String hex = stringToHex(name)+"-"+stringToHex(surname)+"-"+stringToHex(id);
		String link = hex+"-"+charName+charSurname+";";
		System.out.println(link);
		
		String[] decode = link.split("-");
		
		name = hexToString(decode[0]);
		System.out.println("Name: "+ name);
		System.out.println();
		
		surname = hexToString(decode[1]);
		System.out.println("Surname: "+ surname);
		System.out.println();
		
		id = hexToString(decode[2]);
		
		charName = decode[3].split("S")[0].substring(1);
		charSurname = decode[3].split("S")[1].substring(0, decode[3].split("S")[1].length()-1);
		
		System.out.println("Name: "+ name+"\nCharacter:"+charName+";");
		System.out.println();
		
			System.out.println(charName.length());
	
		if(charName.length()>0)
			name = decodeAccentsCharacter(name,charName);
		
		System.out.println("Surname: "+ surname+"\nCharacter: "+charSurname);
		System.out.println();
		if(charSurname.length()>0)
			surname = decodeAccentsCharacter(surname,charSurname);
		
		System.out.println(name);
		System.out.println(surname);
		System.out.println(id);
		
		
//		User user = new User(0, "Fabrizio", "Franzè", "franze.fabrizio@gmail.com", "3455028163", "franze.fabrizio@gmail.com", "blabla", 'Y');
//		
//		java.util.Properties prop = Properties.getProperties("Properties.properties");
//		java.util.Properties emailProp = new java.util.Properties();
//		emailProp.put("mail.smtp.host", prop.getProperty("mail.smtp.host"));
//		emailProp.put("mail.smtp.auth", prop.getProperty("mail.smtp.auth"));
//		emailProp.put("mail.smtp.user", prop.getProperty("mail.smtp.user"));
//		emailProp.put("mail.smtp.port", Integer.parseInt(prop.getProperty("mail.smtp.port")));		
//		emailProp.put("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable"));
//		Session sessione = Session.getDefaultInstance(emailProp);
//		Message mm = new MimeMessage(sessione);
//		mm.setSubject(prop.getProperty("mail.subject"));
//		mm.setContent(String.format(prop.getProperty("mail.text"), user.getName(),user.getSurname(),Utility.encodeLinkRegistration(user)),"text/html; charset=utf-8");
//		InternetAddress fromAddress = new InternetAddress(prop.getProperty("mail.from"));
//	    InternetAddress toAddress = new InternetAddress(user.getEmail());
//	    mm.setFrom(fromAddress);
//	    mm.setRecipient(Message.RecipientType.TO, toAddress);
//	    Transport transport = sessione.getTransport("smtp");
//	    transport.connect("smtp.gmail.com", "franze.fabrizio@gmail.com", "Madness is like gravity");
//	    transport.sendMessage(mm, mm.getAllRecipients());
//	    transport.close();
//		

	}
	
	private static Response loginSupport(boolean remember, AccountDAO accountdao,User user) throws SQLException, IOException{
		if(remember){
			accountdao.setToken(Utility.generatesToken());
			accountdao.update();
			ObjectMapper om = new ObjectMapper();
			Map<String,Object> map1 = om.readValue(om.writeValueAsString(user),Map.class);
			Map<String,Object> map2 = om.readValue("{\"token\": \""+accountdao.getToken()+"\"}",Map.class);
			Map<String, Object> merged = new HashMap<String, Object>(map2);
			merged.putAll(map1);
			return Response.status(200).entity(new ObjectMapper().writeValueAsString(merged)).header("Access-Control-Allow-Origin", "*").build();
		}else{
			accountdao.setToken("");
			accountdao.save();
			return Response.status(200).entity(new ObjectMapper().writeValueAsString(user)).header("Access-Control-Allow-Origin", "*").build();
		}
	}
 
	private static String encodeAccentsCharacter(String string,char check){
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
	
	private static String decodeAccentsCharacter(String string, String characters){
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
 
    private static String hexToString(String string)
    {
        byte [] txtInByte = new byte [string.length() / 2];
        int j = 0;
        for (int i = 0; i < string.length(); i += 2)
        {
                txtInByte[j++] = Byte.parseByte(string.substring(i, i + 2), 16);
        }
        return new String(txtInByte);
    }
 
    private static String stringToHex(String string)
    {
    	char[] hex_chars = "0123456789abcdef".toCharArray();
    	byte[] buf = string.getBytes();
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = hex_chars[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = hex_chars[buf[i] & 0x0F];
        }
        return new String(chars);
    }
}
