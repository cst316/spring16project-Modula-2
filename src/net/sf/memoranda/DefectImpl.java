package net.sf.memoranda;

import java.util.Date;

public class DefectImpl implements Defect {
	
	private Date datefound;
	private int number;
	private String type;
	private String injection;
	private String removal;
	private int approximatefixtime;
	private int fixtime;
	private String fixreference;
	private String description;
	
	protected DefectImpl(Date df, int n, String t, String i, String r, int aft, int ft,
			String fr, String des) {
		datefound = df;
		number = n;
		type = t;
		injection = i;
		removal = r;
		approximatefixtime = aft;
		fixtime = ft;
		fixreference = fr;
		description = des;
	}


	@Override
	public void setDateFound() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Date getDateFound() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setNumber() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setType() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setInjection() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getInjection() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setRemoval() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getRemoval() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setApproximateFixTimeInMinutes() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getApproximateFixTimeInMinutes() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setFixTimeInMinutes() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getFixTimeInMinutes() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setDateRemoved() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Date getDateRemoved() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setFixReference() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getFixReference() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDescription() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
