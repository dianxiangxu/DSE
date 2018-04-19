package dse.nazmul;

public class ConditionMetrics {

	private String conditionStatement;
	private int lineNumber;
	private String className;
	private String methodName;
	
	public ConditionMetrics()
	{
	}
	public ConditionMetrics(String condition, int lineNum)
	{
		this.conditionStatement = condition;
		this.lineNumber = lineNum;
	}
	
	public String getConditionStatement() {
		return conditionStatement;
	}
	public void setConditionStatement(String conditionStatement) {
		this.conditionStatement = conditionStatement;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
}
