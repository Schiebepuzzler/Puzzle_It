package de.logic;

public class GameTimer {
	
    private long _init, _now, _time, _paused;
    private boolean _pause;

	public GameTimer() {
		_init = System.currentTimeMillis();
		_pause = false;
	}
	
	//Getter-Methode Zeit
	public String getformatedTime() {
		_now = System.currentTimeMillis();
		_time = _init - _now;
		
		_time = Math.abs(_time);
		
		long sec = _time / 1000 % 60;
		
		String seconds = String.valueOf(sec);
		if (sec == 0){
			seconds = "00";
		} else if (sec <10 && sec > 0){
			seconds = "0"+sec;
		}
		
		long min = _time / 1000 / 60;
		
		String minutes = String.valueOf(min);
		if (min == 0){
			minutes = "00";
		} else if (min <10 && min > 0){
			minutes = "0"+min;
		}
		
		return minutes + ":" + seconds;
	}
	
	//Wenn Uhr Läuft dann Pause indem Zeit abgespeichert wird
	//Wenn Pause Uhr wieder starten
	public void Pause(){
		
		if (!_pause){
			_paused = System.currentTimeMillis();
		}else{
			_init += System.currentTimeMillis() - _paused;
		}
		
	}
}
