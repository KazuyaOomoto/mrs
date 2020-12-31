package jp.co.mgnc.business.employee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.mgnc.business.common.dao.CommonDAO;
import jp.co.mgnc.business.common.exception.DaoException;
import jp.co.mgnc.business.employee.dto.Employee;

/**
 * 従業員情報を操作するDAOクラスです。
 * @author Oomoto Kazuya
 * @version 1.0
 */
public class EmployeeDAO extends CommonDAO {

	//TODO:４：EMP_NOとPASSWORDから従業員情報を取得するSQL
	private static final String SELECT_BY_ID_PASS = "SELECT emp_no, password, department, emp_name, default_tel FROM m_employee WHERE emp_no=? AND password=crypt(?, password)";
	private static final String SELECT_BY_ID = "SELECT emp_no, password, department, emp_name, default_tel FROM m_employee WHERE emp_no=?";
	private static final String UPDATE = "update m_employee set password=crypt(?, gen_salt('bf')), department=?, emp_name=?, default_tel=? where emp_no=?";
	private static final String UPDATE_TEL_BY_ID = "update m_employee set default_tel=? where emp_no=?";
    
	/**
	 * 従業員番号とパスワードから従業員情報を取り出すメソッドです。
	 * @param emp_id 従業員番号
	 * @return 従業員情報
	 * @throws DaoException 
	 */
	public Employee selectByIdPass(String empNo, String password) throws DaoException{
		
		//戻り値を宣言する。
		Employee employee = null;
		
		try {
			
			//コネクションを取得する。
			getConnection();
			
			//SQL文を設定し、従業員番号、パスワードをセットする。
			PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID_PASS);
			statement.setString(1, empNo);
			statement.setString(2, password);
			
			//SQL文を実行し、Employeeオブジェクトを取得する。
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				employee = new Employee();
				employee.setEmpNo(resultSet.getString("emp_no"));
				employee.setEmpName(resultSet.getString("emp_name"));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDefaultTel(resultSet.getString("default_tel"));
			}
			
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			closeConnection();
		}
		
		return employee;
	}
	
    /**
	 * 従業員番号から従業員情報を取り出すメソッドです。
	 * @param empNo 従業員番号
	 * @return 従業員情報
	 * @throws DaoException 
	 */
	public Employee selectById(String empNo) throws DaoException{
		
		//戻り値を宣言する。
		Employee employee = null;
		
		try {
			
			//コネクションを取得する。
			getConnection();
			
			//SQL文を設定し、従業員番号、パスワードをセットする。
			PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID);
			statement.setString(1, empNo);
			
			//SQL文を実行し、Employeeオブジェクトを取得する。
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				employee = new Employee();
				employee.setEmpNo(resultSet.getString("emp_no"));
				employee.setEmpName(resultSet.getString("emp_name"));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDefaultTel(resultSet.getString("default_tel"));
			}
			
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			closeConnection();
		}
		
		return employee;
	}

    /**
	 * 従業員情報を更新するメソッドです。
	 * @param 従業員情報
	 * @return 0: 更新失敗 1: 更新成功
	 * @throws DaoException 
	 */
	public int update(Employee employee) throws DaoException {
		// 戻り値
		int ret = 0;
		if(selectById(
				employee.getEmpNo()
				) != null) 
		{
			// コネクション
			getConnection();
			
			try {
				// ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(UPDATE);
				pstmt.setString(1,  employee.getPassword());
				pstmt.setString(2,  employee.getDepartment());
				pstmt.setString(3,  employee.getEmpName());
				pstmt.setString(4,  employee.getDefaultTel());
				pstmt.setString(5,  employee.getEmpNo());
				
				// データベースの問い合わせ
				ret = pstmt.executeUpdate();				
			} catch( SQLException e) {
				e.printStackTrace();
			}finally {
				closeConnection();
			}
		}
		return ret;
	}

    /**
	 * 従業員情報の連絡先のみを更新するメソッドです。
	 * @param 従業員情報
	 * @return 0: 更新失敗 1: 更新成功
	 * @throws DaoException 
	 */
	public int updateTelById(Employee employee) throws DaoException {
		// 戻り値
		int ret = 0;
		
		//指定IDの従業員を検出
		if(selectById(
				employee.getEmpNo()
				) != null) 
		{
			// コネクション
			getConnection();
			
			try {
				// ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_TEL_BY_ID);
				pstmt.setString(1,  employee.getDefaultTel());
				pstmt.setString(2,  employee.getEmpNo());
				
				// データベースの問い合わせ
				ret = pstmt.executeUpdate();				
			} catch( SQLException e) {
				e.printStackTrace();
			}finally {
				closeConnection();
			}
		}
		return ret;
	}
}
