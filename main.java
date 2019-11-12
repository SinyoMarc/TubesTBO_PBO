import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class main{


	public static void main(String[] args) {
	  // Jalankan GUI
	  SwingUtilities.invokeLater(new Runnable() {
	     @Override
	     public void run() {

	    JFrame frame = new JFrame("TICTACTOE"); 	
	    JButton button,button1;
		button = new JButton("vs Player");
		button1 = new JButton("vs Bot");
		frame.add(button);
		frame.add(button1);
		frame.setLayout(new FlowLayout());
		frame.setSize(220, 80);  
		frame.setVisible(true); 
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new tictactoe();		
			}
		});

		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JButton button2,button3;
				button2 = new JButton("Bot jalan pertama");
				button3 = new JButton("Player jalan pertama");
				frame.add(button2);
				frame.add(button3);
				frame.setLayout(new FlowLayout());
				frame.setSize(220, 160);  
				frame.setVisible(true);
				button2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
				new botFirst();
				Data data = new Data();
			      data.loadfile("/media/americano/ToshibaMK6475/DarkOrangeCloud/3/OOP/uts/dfa_computer.txt");
			      if (data.currState != data.VALUNDEF){
			         while (!(data.isFinalState(data.currState))){
			            data.output();
			            data.inputalphabet("Masukkan angka lokasi (1-9) = ");
			        }
			            char[] tempArr = data.getStateRepresentation(data.currState).toCharArray();
			            if(tempArr[9]=='w'){
			               System.out.println("SELESAI, komputer menang");
			               data.output();
			            } else if (tempArr[9]=='d'){
			               System.out.println("SELESAI, seri");
			               data.output();
			            } else{
			               System.out.println("ERROR, final state belum terdefinisi");
			            }
			            System.out.println();
			            data.outputHistory();
			      }	
			}
		}); 	button3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
				new playerFirst();	
				Data data = new Data();
			      data.loadfile("/media/americano/ToshibaMK6475/DarkOrangeCloud/3/OOP/uts/dfa_player.txt");
			      if (data.currState != data.VALUNDEF){
			         while (!(data.isFinalState(data.currState))){
			         	data.outputDesc();
			            data.output();
			            data.inputalphabet("Masukkan angka lokasi (1-9) = ");
			        }
			            char[] tempArr = data.getStateRepresentation(data.currState).toCharArray();
			            if(tempArr[9]=='w'){
			               System.out.println("SELESAI, komputer menang");
			               data.output();
			            } else if (tempArr[9]=='d'){
			               System.out.println("SELESAI, seri");
			               data.output();
			            } else{
			               System.out.println("ERROR, final state belum terdefinisi");
			            }
			            System.out.println();
			            data.outputHistory();
			      }		
			}
		}); 

			}
		});
		}
	  });
	}

}

