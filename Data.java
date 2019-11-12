import java.io.*;
import java.util.*;

public class Data extends TipeData{
	/**implementasi dari abstract class TipeData, dan 
	method2 lainnya untuk memproses data dari file**/
	/****/
	TipeData.ArrStates states = new TipeData.ArrStates();
	TipeData.ArrString alphabets = new TipeData.ArrString();
	TipeData.TabInt transtable = new TipeData.TabInt();
	TipeData.ArrInt finalstate = new TipeData.ArrInt();
	TipeData.ArrHistory history = new TipeData.ArrHistory();
	int startState;
	String dataDesc;
	boolean loadSukses;
	int currState;


	/**method mutator melakukan set nilai efektif ukuran array **/
	//Overriding dari TipeData
	public void setNeff(ArrStates arr, int a){
		this.states.Neff = a;
	}
	//Overloading setNeff
	public void setNeff(ArrString arr, int a){
		this.alphabets.Neff = a;
	}

	public void setNeff(ArrInt arr, int a){
		this.finalstate.Neff = a;
	}

	public void setNeff(ArrHistory arr, int a){
		this.history.Neff = a;
	}

	/**method assesor melakukan get nilai efektif ukuran array **/
	public int getNeff(ArrStates arr){
		return this.states.Neff;
	}

	public int getNeff(ArrString arr){
		return this.alphabets.Neff;
	}

	public int getNeff(ArrInt arr){
		return this.finalstate.Neff;
	}

	public int getNeff(ArrHistory arr){
		return this.history.Neff;
	}

	public void initTransition(){
	/** I.S. : Terdapat transitionTable belum diinisialisasi 
	F.S. : transitionTable diisi dengan -99 (VALUNDEF)**/
		int i,j;

		for (i=0; i<NMAX; i++){
			for (j=0; j<NMAX; j++){
				this.transtable.Isi[i][j] = VALUNDEF;
			}
		}
	}
	
	public int getState(String state){
	/**Menerima state berupa tulisan lalu mengoutput versi integernya**/
		int i = 0;
		while ( (i<=getNeff(states)) && (!(state.equals(this.states.Isi[i])))){
			i++;
		}
		// i > Neff atau stattes.Isi == state

		//cek terdefinisi
		if (i>getNeff(states)) {
			i = VALUNDEF;
		}

		return i;
	}

	public int getAlphabet(String alphabet){
	/**Menerima alphabet berupa tulisan lalu mengoutput versi integernya**/
		int i = 0;
		while ( (i<=getNeff(alphabets)) && (!(alphabet.equals(this.alphabets.Isi[i])))){
			i++;
		}
		// i > Neff atau stattes.Isi == state

		//cek terdefinisi
		if (i>getNeff(alphabets)) {
			i = VALUNDEF;
		}

		return i;
	}

	public String getAlphabetLabel(int alphabet){
	/**Menerima alphabet berupa integer lalu mengoutput versi stringnya**/
		return (this.alphabets.Isi[alphabet]);
	}

	public String getStateLabel(int state){
	/**Menerima state berupa integer lalu mengoutput versi stringnya**/
		return (this.states.Isi[state]);
	}

	public String getStateRepresentation(int state){
	/**Menerima state berupa integer lalu mengoutput representationnya**/
		return (this.states.Representation[state]);
	}


	public void readStart (Scanner myReader){
		/**I.S. : Terdapat file text dengan posisi line berada di tulisan [START]**/
  		/**F.S. : startState diisi dari file, posisi line berada di baris kosong**/
		String data = myReader.nextLine();
        this.startState = getState(data);
        data = myReader.nextLine();
	}

	public void readStates (Scanner myReader){
	/**I.S. : Terdapat file text dengan posisi line berada di tulisan
          [STATE]/[STATES]**/
    /**F.S. : Array states diisi dari file, posisi line berada di baris kosong**/
        int i = 0;
        String data;
        String kosong = "";
	    do{
	    	//Baca baris
	      data = myReader.nextLine();
	      if(!(data.equals(kosong))){
	      	int j = 0;
	      	String temp = "";
	      	int part = 1;
	      	char cc;
	      	char[] tempArr = data.toCharArray();
	      	
	      	//Parsing per karakter

	      	while (j<tempArr.length){
	           cc = tempArr[j];
	           //Jika menemui pembatas proses
	           if (cc == '|'){
	           	if (part==1) {
	           		this.states.Isi[i] = temp;
	           	} else if (part == 2){
	           		this.states.Representation[i] = temp;
	           	}
	           	temp = "";
	           	part++;
	           } else {
	           	if (cc != ' ') {
	           		temp = temp.toString()+cc;
	           	}
	           }
	           j++;
	      }
	        //j = tempArr.length
	        i++;
	    }
	    } while(!(data.equals(kosong)));
	    //data = ''
	    setNeff(states, i-1);
	}

	public void readFinal (Scanner myReader){
		/**I.S. : Terdapat file text dengan posisi line berada di tulisan
          [FINAL]/[FINALS]**/
  		/**F.S. : Array finalState diisi dari file, posisi line berada di baris kosong**/
		int i = 0;
        String data;
        String kosong = "";
         do{
	    	//Baca baris
	      data = myReader.nextLine();
	      if(!(data.equals(kosong))){
	      	
	      	
	      	this.finalstate.Isi[i] = getState(data);

	      	//cek final state terdaftar
	      	if (finalstate.Isi[i] == VALUNDEF){
	      		loadSukses = false;
	      	
	      } 
	      }i++;
	    }while(!(data.equals(kosong)));
	    setNeff(finalstate, i-2);
	}

	public void readAlphabets (Scanner myReader){
	/**I.S. : Terdapat file text dengan posisi line berada di tulisan
          [ALPHABET]/[ALPHABETS]**/
    /**F.S. : Array alphabets diisi dari file, posisi line berada di baris kosong**/
		int i = 0;
        String data;
        String kosong = "";
         do{
	    	//Baca baris
	      data = myReader.nextLine();
	      if(!(data.equals(kosong))){
	      	
	      	this.alphabets.Isi[i] = data;

	      	//cek final state terdaftar
	      	if (finalstate.Isi[i] == VALUNDEF){
	      		loadSukses = false;
	      	}
	      }i++;
	    }while(!(data.equals(kosong)));
	    setNeff(alphabets, i-2);
	}


	public void readtranstable (Scanner myReader){
		/**I.S. : Terdapat file text dengan posisi line berada di tulisan
          [TRANSITION-T]/[TRANSITIONS-T]**/
 		/**F.S. : Tabel transitionTable diisi dari file, kolom sebagai alphabet,
          baris sebagai from state, dan isi sebagai to state.
          Posisi line berada di baris kosong**/
	    TipeData.ArrInt tempInput = new TipeData.ArrInt();
	    String kosong = "";
	    int i;
	    int tempFromState =0;
	    String data;
	    char cc;
	    String ingested;
	    int part;

	    //proses per baris
	    int baris = 0;
		 do{
	    	//Baca baris
	    	baris++;
	      data = myReader.nextLine();
	      if(!(data.equals(kosong))){
			i = 0;
			ingested =""; 	//String yang telah diingest
	      	part = 1;	
	      	char[] tempArr = data.toCharArray();
	      	
	      	//Parsing per karakter

	      	while (i<tempArr.length){
	           cc = tempArr[i];	  //Karakter sekarang
	           //Jika menemui pembatas proses
	           if (cc == '|'){
	           	//untuk baris 1, jadikan urutan input
	           	if (baris==1) {
	           		tempInput.Isi[part] = getAlphabet(ingested);
	           		if (tempInput.Isi[part] == VALUNDEF){
			      		loadSukses = false;
			      	}
	           	} else{
	           		if (part==1){
	           			tempFromState = getState(ingested);
	           			if (tempFromState == VALUNDEF){
			      		loadSukses = false;
			      		}
	           		} else{
	           			this.transtable.Isi[tempFromState][tempInput.Isi[part-1]] = getState(ingested);
	           			if (this.transtable.Isi[tempFromState][tempInput.Isi[part-1]] == VALUNDEF){
	           				loadSukses = false;
	           			}
	           		}
	           	}
	           	part++;
	           	ingested = "";
	           } else {
	           	if (cc != ' ') {
	           		ingested = ingested.toString()+cc;
	           	}
	           }
	           i++;
		      }
		    }
	    } while(myReader.hasNextLine());
	}


	public void loadfile (String namaFile){
	/**I.S. : Terdapat file dengan namaFile yang telah diformat sesuai ketentuan.
          Ketentuan file adalah terdapat states,start state, final state,
          transition, dan alphabet.
          Setiap memulai kategori ditandai dengan tanda kurung siku,
          contoh: [STATES]
          Setelah header, ditulis isinya dengan pemisah berupa enter.
          Saat sudah semua, berikan baris kosong tambahan untuk memisahkan ke
          kategori berikut.
          Khusus untuk transition ditulis dengan format from|input|to,
          contoh: S0|X|S1}
    F.S. : File sesuai namaFile dibaca dan isinya diisikan ke states, alphabets,
          finalState, startState, dan transitionTable**/
		try {
	      File myObj = new File(namaFile);
	      Scanner myReader = new Scanner(myObj);

	      /*setup variabel*/
	      this.initTransition();
	      this.loadSukses = true;

	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        switch(data) {
	        	case "[DESC]" :
	        		data = myReader.nextLine();
	        		this.dataDesc= data;
	        		data = myReader.nextLine();
	        		break;
	        	case "[STATES]" :
				    this.readStates(myReader);
	        		break;
	        	case "[START]" :
	        		this.readStart(myReader);
	        		break;
	        	case "[FINAL]" :
	        		this.readFinal(myReader);
	        		break;
	        	case "[ALPHABETS]" :
	        		this.readAlphabets(myReader);
	        		break;
	        	case "[TRANSITIONS-T]" :
	        		this.readtranstable(myReader);
	        		break;
	        	default :
	            	System.out.println("Invalid input");
	        }
	        currState = startState;
	        this.history.States[0] = getStateLabel(this.startState);
	        this.history.Representation[0] = getStateRepresentation(this.startState);
	        setNeff(history,0);  
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	}

	public void inputalphabet(String prompt){
	/**I.S. : DFA sudah diload, currState terdefinisi**/
  	/**F.S. : Meminta input user lalu mengubah currState sesuai input dan
          transitionTable**/
          int alphabet;
          Scanner scan = new Scanner(System.in);
		  String input = scan.next();

		  alphabet = getAlphabet(input);

		  if (alphabet != VALUNDEF){
		  	transition(alphabet);
		  }

          	
	}
  	
	public void output(){
	/**I.S. : DFA sudah diload, currState terdefinisi**/
  	/**F.S. : Tercetak kondisi papan sekarang**/
    	String stringState = getStateRepresentation(currState);
    	char[] tempArr = stringState.toCharArray();
    	char currcode;

    	for (int i =0; i<=8; i++){
    		currcode = tempArr[i];
    		if (currcode != '0') {
    			System.out.print(currcode);
    		} else {
    			System.out.print(' ');
    		}

    		if (((i+1) % 3) == 0){
    			System.out.println();
    		} else {
    			System.out.print('|');
    		}

    	}

	}

	public void outputHistory(){
	/**I.S. : currState adalah final state, history.neff > 2
  	F.S. : Tercetak semua state yang dilalui**/
  		int i;
  		if (isFinalState(currState)){
  			System.out.println("States yang dilewati");

  			for (i=0;i<getNeff(history);i++){
  				System.out.println(this.history.States[i]+" "+this.history.Representation[i]);
  				System.out.println(" ↓ "+this.history.Input[i]);
  			}
  			System.out.println(this.history.States[i]+" "+this.history.Representation[i]);
  		}
          	
	}

	public void outputDesc(){
		System.out.println(this.dataDesc);
	}

	public void transition(int inputAlphabet){
	/**I.S. : DFA sudah diload, currState terdefinisi, inputAlphabet terdefinisi**/
  	/**F.S. : Mengubah currState sesuai inputAlphabet dan ransitionTable**/
          int nextState = getNextState(inputAlphabet);

          if ((nextState != VALUNDEF) && (nextState<getNeff(states))){
		  	this.history.Input[getNeff(history)]  = getAlphabetLabel(inputAlphabet);
     		setNeff(history, getNeff(history)+1);
      		this.history.States[getNeff(history)] = getStateLabel(nextState);
      		this.history.Representation[getNeff(history)] = getStateRepresentation(nextState);

      		currState = nextState;
		  } 
	}

	public int getNextState(int inputAlphabet){
	/**Menerima inputAlphabet lalu mengeluarkan state yang akan dituju berdasarkan
   	transitionTable**/	
   		return this.transtable.Isi[currState][inputAlphabet];
	}

	public boolean isFinalState(int state){
	/**Menerima input state sekarang (format sudah integer) dan cek apa final**/
		int i =0;
		boolean fin = false;

		while(!(fin) && (i<getNeff(finalstate))){
			if (this.finalstate.Isi[i] == state){
				fin = true;
			} else {
				i++;
			}
		}

		return fin;	
	}
  	
}