package analytics;
/**
 * 
 */

/**
 * This class uses the PADITable and dive features computed by DiveAnalyser to interpret a dive. The results are stored back into DiveFeatures.
 * @author csc242
 *
 */
public class DiveInterpreter {

	private DiveFeatures diveFeatures;

	public DiveInterpreter(DiveFeatures diveFeatures) {
		// 
		this.diveFeatures = diveFeatures;
	}

	public DiveFeatures interpretDive() {
		// 
		if(diveFeatures.isRealDive()){		
			if(diveFeatures.getNumOfDivelets()==2){
				diveFeatures.getFirstDiveletFeatures().setExcessDiveDepth(diveFeatures.getFirstDiveletFeatures().getDiveDepth()-PADITable.safeDepthLimitInMeters);
				diveFeatures.getFirstDiveletFeatures().setExcessBottomTime(diveFeatures.getFirstDiveletFeatures().getBottomTime()-PADITable.getNDL(diveFeatures.getFirstDiveletFeatures().getDiveDepth()));
				diveFeatures.getFirstDiveletFeatures().setSafetyStopRequired(PADITable.needSafetyStop(diveFeatures.getFirstDiveletFeatures().getDiveDepth(), diveFeatures.getFirstDiveletFeatures().getBottomTime()));
				diveFeatures.getFirstDiveletFeatures().setExcessAscentSpeed(diveFeatures.getFirstDiveletFeatures().getAscentSpeed()-PADITable.avgAscentLimit);
				diveFeatures.setStartPressureGroup(PADITable.getStartPressureGroup(diveFeatures.getFirstDiveletFeatures().getDiveDepth(),diveFeatures.getFirstDiveletFeatures().getBottomTime()));
				diveFeatures.setEndPressureGroup(PADITable.getEndPressureGroup(diveFeatures.getStartPressureGroup(),diveFeatures.getSurfaceIntervalTime()));
				
				diveFeatures.getSecondDiveletFeatures().setExcessDiveDepth(diveFeatures.getSecondDiveletFeatures().getDiveDepth()-PADITable.safeDepthLimitInMeters);
				diveFeatures.getSecondDiveletFeatures().setExcessBottomTime(diveFeatures.getSecondDiveletFeatures().getBottomTime()-PADITable.getANDL(diveFeatures.getSecondDiveletFeatures().getDiveDepth(),diveFeatures.getEndPressureGroup()));
				diveFeatures.getSecondDiveletFeatures().setSafetyStopRequired(PADITable.needSafetyStop(diveFeatures.getSecondDiveletFeatures().getDiveDepth(), diveFeatures.getSecondDiveletFeatures().getBottomTime()));
				diveFeatures.getSecondDiveletFeatures().setExcessAscentSpeed(diveFeatures.getSecondDiveletFeatures().getAscentSpeed()-PADITable.avgAscentLimit);
			}
			else{
				diveFeatures.getFirstDiveletFeatures().setExcessDiveDepth(diveFeatures.getFirstDiveletFeatures().getDiveDepth()-PADITable.safeDepthLimitInMeters);
				diveFeatures.getFirstDiveletFeatures().setExcessBottomTime(diveFeatures.getFirstDiveletFeatures().getBottomTime()-PADITable.getNDL(diveFeatures.getFirstDiveletFeatures().getDiveDepth()));
				diveFeatures.getFirstDiveletFeatures().setSafetyStopRequired(PADITable.needSafetyStop(diveFeatures.getFirstDiveletFeatures().getDiveDepth(), diveFeatures.getFirstDiveletFeatures().getBottomTime()));
				diveFeatures.getFirstDiveletFeatures().setExcessAscentSpeed(diveFeatures.getFirstDiveletFeatures().getAscentSpeed()-PADITable.avgAscentLimit);
			}
		}
		return diveFeatures;
	}

}
