package cn.safetys.sync.mq;

public class SyncTriggerService {

	private SyncClient syncClient;

	
	public void updateCompanyGov(Long companyId){
		syncClient.triggerJobGov("DA_COMPANY", companyId);
	}
	
	public void updateCompanyEs(Long companyId){
		syncClient.triggerJobEs("DA_COMPANY", companyId);
	}
	
	public void updateFkUserGov(Long userId){
		syncClient.triggerJobGov("FK_USER", userId);
	}
	
	public void updateNomalDangerEs(){
		syncClient.triggerRunEs("DA_NORMAL_DANGER");
	}
	public void updateNomalDangerGov(){
		syncClient.triggerRunGov("DA_NORMAL_DANGER");
	}
	
	public void updateDangerEs(Long dangerId){
		syncClient.triggerJobEs("DA_DANGER",dangerId);
	}
	public void updateDangerGov(Long dangerId){
		syncClient.triggerJobGov("DA_DANGER",dangerId);
	}
	
	public void updateDangerGorverEs(Long id){
		syncClient.triggerJobEs("DA_DANGER_GORVER",id);
	}
	public void updateDangerGorverGov(Long id){
		syncClient.triggerJobGov("DA_DANGER_GORVER",id);
	}
	
	public void updateQuarterReportEs(Long id){
		syncClient.triggerJobEs("DA_COMPANY_QUARTER_REPORT", id);
	}
	
	public void setSyncClient(SyncClient syncClient) {
		this.syncClient = syncClient;
	}
}
