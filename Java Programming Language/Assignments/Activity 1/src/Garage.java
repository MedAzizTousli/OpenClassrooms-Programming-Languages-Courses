import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import voiture.Vehicule;


public class Garage implements Serializable{

	private List<Vehicule> listVoitures = new ArrayList<>();
	
	
	String saveFile = "garage.txt";
	public Garage(){
		readSavedFile();		
	}
	
	private void readSavedFile(){
		try ( FileInputStream fis = new FileInputStream(saveFile); 
				BufferedInputStream bis = new BufferedInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(bis) ) {
				    listVoitures = (List<Vehicule>)ois.readObject();
				    ois.close();
		}	catch (FileNotFoundException e) {
		      System.err.println("Aucune voiture sauvegardée ! ");
	    } catch (IOException e) {
	    	System.err.println("Erreur de lecture du fichier de sauvegarde !");
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
	}
	
	private void writeSavedFile(){
		try ( FileOutputStream fis = new FileOutputStream(saveFile); 
				BufferedOutputStream bis = new BufferedOutputStream(fis);
				ObjectOutputStream ois = new ObjectOutputStream(bis) ) {
				   ois.writeObject(listVoitures);
				   ois.close();
		}	catch (FileNotFoundException e) {
		      System.err.println("Aucun fichier trouvé ! ");
	    } catch (IOException e) {
	    	System.err.println("Erreur d'écriture dans le fichier de sauvegarde !");
	    }
	}
	
	
	public void addVoiture(Vehicule v){
		listVoitures.add(v);
		writeSavedFile();
	}
	
	public String toString(){
		String str;
		str =  "****************************\n";
		str += "*  Garage OpenClassrooms   *\n";
		str += "****************************\n";
		
		for (Vehicule v : listVoitures)
			str += " + " + v + "\n";
		
		return str;
	}
	
}
