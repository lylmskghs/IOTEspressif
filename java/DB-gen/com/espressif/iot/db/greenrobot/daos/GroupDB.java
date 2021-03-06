package com.espressif.iot.db.greenrobot.daos;

import com.espressif.iot.db.greenrobot.daos.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import com.espressif.iot.object.db.IGroupDB;

// KEEP INCLUDES END
/**
 * Entity mapped to table GROUP_DB.
 */
public class GroupDB implements IGroupDB
{
    
    private long id;
    
    /** Not-null value. */
    private String name;
    
    /** Not-null value. */
    private String userKey;
    
    private int state;
    
    private String localDeviceBssids;
    
    private String cloudDeviceBssids;
    
    private String removeDeviceBssids;
    
    /** Used to resolve relations */
    private transient DaoSession daoSession;
    
    /** Used for active entity operations. */
    private transient GroupDBDao myDao;
    
    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END
    
    public GroupDB()
    {
    }
    
    public GroupDB(long id)
    {
        this.id = id;
    }
    
    public GroupDB(long id, String name, String userKey, int state, String localDeviceBssids, String cloudDeviceBssids,
        String removeDeviceBssids)
    {
        this.id = id;
        this.name = name;
        this.userKey = userKey;
        this.state = state;
        this.localDeviceBssids = localDeviceBssids;
        this.cloudDeviceBssids = cloudDeviceBssids;
        this.removeDeviceBssids = removeDeviceBssids;
    }
    
    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession)
    {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGroupDBDao() : null;
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    /** Not-null value. */
    public String getName()
    {
        return name;
    }
    
    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /** Not-null value. */
    public String getUserKey()
    {
        return userKey;
    }
    
    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUserKey(String userKey)
    {
        this.userKey = userKey;
    }
    
    public int getState()
    {
        return state;
    }
    
    public void setState(int state)
    {
        this.state = state;
    }
    
    public String getLocalDeviceBssids()
    {
        return localDeviceBssids;
    }
    
    public void setLocalDeviceBssids(String localDeviceBssids)
    {
        this.localDeviceBssids = localDeviceBssids;
    }
    
    public String getCloudDeviceBssids()
    {
        return cloudDeviceBssids;
    }
    
    public void setCloudDeviceBssids(String cloudDeviceBssids)
    {
        this.cloudDeviceBssids = cloudDeviceBssids;
    }
    
    public String getRemoveDeviceBssids()
    {
        return removeDeviceBssids;
    }
    
    public void setRemoveDeviceBssids(String removeDeviceBssids)
    {
        this.removeDeviceBssids = removeDeviceBssids;
    }
    
    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete()
    {
        if (myDao == null)
        {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    
    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update()
    {
        if (myDao == null)
        {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    
    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh()
    {
        if (myDao == null)
        {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    
    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END
    
}
