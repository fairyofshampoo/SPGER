/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AccessAccount;
import mx.uv.fei.sspger.logic.AccessAccount;

/**
 *
 * @author miche
 */
public interface IAccessAccount {
    int addAccessAccount(AccessAccount accessAccount) throws SQLException;
    AccessAccount getAccessAccount(String email) throws SQLException;
    List <AccessAccount> getAllAccessAccounts() throws SQLException;
}
