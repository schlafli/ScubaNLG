package analytics;

/*
 * Main.java
 *
 * Created on 25 September 2006, 16:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 **/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * This class implements the basic functionality required to connect and execute
 * a database query. The results are stored into a table model for easy access
 * from a client application.
 * 
 * @author ssripada
 */
public class MyJDBC extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	Connection					connection;
	Statement					statement;
	ResultSet					resultSet;
	String[]					columnNames			= {};
	Vector<Object>				rows				= new Vector<Object>();
	ResultSetMetaData			metaData;
	
	/** Creates a new instance of MyJDBC */
	public MyJDBC(String url, String driver, String user, String passwd) {
		try {
			Class.forName(driver);// );
			connection = DriverManager.getConnection(url, user, passwd);// );
			statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			
		} catch (java.lang.ClassNotFoundException ex) {
			System.out.println("Driver Not Loaded");
		} catch (SQLException ex) {
			System.err.println("Cannot connect to this database.");
			System.err.println(ex);
		}
	}
	
	public ResultSet executeQuery(String query) {
		if (connection == null || statement == null) {
			System.err.println("There is no database to execute the query.");
			return null;
		}
		try {
			resultSet = statement.executeQuery(query);
			metaData = resultSet.getMetaData();
			
			int numberOfColumns = metaData.getColumnCount();
			columnNames = new String[numberOfColumns];
			// Get the column names and cache them.
			// Then we can close the connection.
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}
			
			// Get all rows.
			rows = new Vector<Object>();
			while (resultSet.next()) {
				Vector<Object> newRow = new Vector<Object>();
				for (int i = 1; i <= getColumnCount(); i++) {
					newRow.addElement(resultSet.getObject(i));
				}
				rows.addElement(newRow);
			}
			fireTableChanged(null); // Tell the listeners a new table has
									// arrived.
		} catch (SQLException ex) {
			System.err.println(ex);
		}
		return resultSet;
	}
	
	public void close() throws SQLException {
		System.out.println("Closing db connection");
		resultSet.close();
		statement.close();
		connection.close();
	}
	
	// MetaData
	
	public String getColumnName(int column) {
		if (columnNames[column] != null) {
			return columnNames[column];
		} else {
			return "";
		}
	}
	
	public Class getColumnClass(int column) {
		int type;
		try {
			type = metaData.getColumnType(column + 1);
		} catch (SQLException e) {
			return super.getColumnClass(column);
		}
		
		switch (type) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				return String.class;
				
			case Types.BIT:
				return Boolean.class;
				
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
				return Integer.class;
				
			case Types.BIGINT:
				return Long.class;
				
			case Types.FLOAT:
			case Types.DOUBLE:
				return Double.class;
				
			case Types.DATE:
				return java.sql.Date.class;
				
			default:
				return Object.class;
		}
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return rows.size();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vector<Object> row = (Vector<Object>) rows.elementAt(rowIndex);
		return row.elementAt(columnIndex);
	}
}
