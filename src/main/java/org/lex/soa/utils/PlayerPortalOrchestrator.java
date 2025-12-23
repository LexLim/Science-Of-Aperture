package org.lex.soa.utils;

public interface PlayerPortalOrchestrator {

    public void onLeave();
    public void onJoin();

    public void save();
    public void restore();

    public void updatePortals();
    public void setPortals();
    public void clearPortals();
    public void getPortals();

}