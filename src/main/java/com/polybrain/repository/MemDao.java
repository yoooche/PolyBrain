package com.polybrain.repository;


import com.polybrain.model.MemTO;


import java.util.List;

public interface MemDao {
    public List<MemTO> selectAll();
    MemTO selectByMemName(String memName);
    void createMember(MemTO member);
    void createMemberJDBC(MemTO member);
    MemTO getMemberById(Integer memNo);
    void updateMember(MemTO member);
    void deleteMember(Integer memNo);
    List<MemTO> getAllMembers();
    MemTO selectByMemEmailAndPwd(String memEmail, String memPwd);
    MemTO selectById(Integer memNo);



    public List<MemTO> getAll();

    public MemTO findByPrimaryKey(Integer memNo);

    public void delete(Integer memNo);

    public void updateJDBC(MemTO memNo);

    public List<MemTO> GETEmailPwd();

    // 检查是否存在具有相同电子邮件的会员
    boolean isEmailExists(String email);

    // 检查是否存在具有相同电子邮件的会员
    boolean isPidExists(String pid);

    // 检查是否存在具有相同电子邮件的会员
    boolean isPhoneExists(String phone);


    public void updateInformation(MemTO memNo);

    public MemTO getMemberByMemNo(Integer memNo);

}
