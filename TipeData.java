import java.util.*;
/*Kelas abstrak sebagai penyimpan tipe data yang akan digunakan*/

public abstract class TipeData{
	public static final int NMAX = 100;
	public static final int VALUNDEF = -99;

	class TabInt{
		int[][] Isi = new int[NMAX][NMAX];
		int NKol; 
		int NBar; 
	}

	class ArrString{
		String[] Isi = new String[NMAX];
		int Neff;
	}

	class ArrStates{
		String[] Isi = new String[NMAX];
		String[] Representation = new String[NMAX];
		int Neff;
	}

	class ArrInt{
		int[] Isi = new int[NMAX];
		int Neff;
	}

	class ArrHistory{
		String[] States = new String[NMAX];
		String[] Input = new String[NMAX];
		String[] Representation = new String[NMAX];
		int Neff;
	}

	public abstract void setNeff(ArrStates arr, int a);
	public abstract void setNeff(ArrString arr, int a);
	public abstract void setNeff(ArrInt arr, int a);
	public abstract void setNeff(ArrHistory arr, int a);
	public abstract int getNeff(ArrStates arr);
	public abstract int getNeff(ArrString arr);
	public abstract int getNeff(ArrInt arr);
	public abstract int getNeff(ArrHistory arr);


	public abstract int getState(String state);
	public abstract int getAlphabet(String alphabet);
	public abstract String getAlphabetLabel(int alphabet);
	public abstract String getStateLabel(int state);
	public abstract String getStateRepresentation(int state);



}

