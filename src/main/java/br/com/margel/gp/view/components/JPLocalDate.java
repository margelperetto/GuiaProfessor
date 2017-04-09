package br.com.margel.gp.view.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import com.michaelbaranov.microba.calendar.DatePicker;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class JPLocalDate extends JPanel{

	private DatePicker dpData = new DatePicker();

	private SequentialNumberField snfHora = new SequentialNumberField(0, 23);
	private SequentialNumberField snfMinuto = new SequentialNumberField(0, 59);

	public JPLocalDate(boolean showTime) {
		dpData.setShowTodayButton(true);
		dpData.setShowNoneButton(true);
		
		final JTextComponent comp = (JTextComponent)dpData.getComponent(0);
		comp.addFocusListener(new FocusAdapter() {
			private SimpleDateFormat sdf = new SimpleDateFormat();
			@Override
			public void focusLost(FocusEvent evt) {
				try {
					String text = comp.getText();
					if (text.isEmpty()) {
						dpData.setDate((Date)null);
					}else if (text.length() == 6) {
						sdf.applyPattern("ddMMyy");
						dpData.setDate(sdf.parse(text));
					}else if (text.length() == 8) {
						sdf.applyPattern("ddMMyyyy");
						dpData.setDate(sdf.parse(text));
					}
				} catch (Exception e) {}
			}
		});

		setLayout(new MigLayout(new LC().insetsAll("0").gridGapX("0")));
		add(dpData, new CC().width("120::"));
		if(showTime){
			add(new JLabel(" "));
			add(snfHora, new CC().width("30::"));
			add(new JLabel(":"));
			add(snfMinuto, new CC().width("30::"));
		}
		snfHora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT && snfHora.cursorNoFinal()){
					snfMinuto.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT && snfHora.getCaretPosition()==0){
					comp.requestFocus();
				}
			}
		});
		snfMinuto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT && snfMinuto.getCaretPosition()==0){
					snfHora.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT && snfMinuto.cursorNoFinal()){
					snfMinuto.transferFocus();
				}
			}
		});
		clear();
	}

	public void clear(){
		try {
			dpData.setDate((Date)null);
			snfHora.setValue(0);
			snfMinuto.setValue(0);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void setLocalDateTime(LocalDateTime data){
		try {
			if(data==null){
				clear();
				return;
			}
			Integer hora = data.getHour();
			Integer minuto = data.getMinute();

			LocalDate localDate = data.toLocalDate();

			dpData.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

			snfHora.setValue(hora);
			snfMinuto.setValue(minuto);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public LocalDateTime getLocalDateTime(){
		try {
			Date date = dpData.getDate();
			if(date==null){
				return null;
			}
			Integer hora = snfHora.getIntValue();
			Integer minuto = snfMinuto.getIntValue();

			LocalDateTime ret = Instant.ofEpochMilli(date.getTime())
					.atZone(ZoneId.systemDefault()).toLocalDateTime();

			ret.withHour(hora);
			ret.withMinute(minuto);
			ret.withSecond(59);

			return ret;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public LocalDate getLocalDate() {
		LocalDateTime date = getLocalDateTime();
		return date==null?null:date.toLocalDate();
	}

	public void setLocalDate(LocalDate date) {
		setLocalDateTime(date==null?null:LocalDateTime.of(date,LocalTime.MAX));
	}

	public void setEditable(boolean editable) {
		((JTextComponent)dpData.getComponent(0)).setEditable(editable);
		dpData.getComponent(1).setEnabled(false);
		snfHora.setEditable(editable);
		snfMinuto.setEditable(editable);
	}

}
