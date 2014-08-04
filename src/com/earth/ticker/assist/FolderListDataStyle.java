package com.earth.ticker.assist;

public class FolderListDataStyle {
        private String id;
        private String title;
        private boolean hasParent;
        private boolean hasChild;
        private boolean expanded;
        private String parent;
        private int level;
        
        public FolderListDataStyle(String id,String title,boolean hasParent,
        		boolean hasChild,boolean expanded,String parent,int level)
        {
        	super();
        	this.id=id;
        	this.title=title;
        	this.hasParent=hasParent;
        	this.hasChild=hasChild;
        	this.expanded=expanded;
        	this.parent=parent;
        	this.level=level;
        }
        
        public String getId()
        {
        	return id;
        }
        
        public void setId(String id)
        {
        	this.id=id;
        }
        
        public String getTitle()
        {
        	return title;
        }
        
        public void setTitle(String title)
        {
        	this.title=title;
        }
        
        public boolean isHasParent()
        {
        	return hasParent;
        }
        
        public void setHasParent(boolean hasParent)
        {
        	this.hasParent=hasParent;
        }
        
        public  boolean isHasChild()
        {
        	return hasChild;
        }
        
        public void setHasChild()
        {
        	this.hasChild=hasChild;
        }
        
        public String getParent()
        {
        	return parent;
        }
        
        public void setParent(String parent)
        {
        	this.parent=parent;
        }
        
        public int getLevel()
        {
        	return level;
        }
        
        public void setLevel(int level)
        {
        	this.level=level;
        }
        
        public boolean isExpanded()
        {
        	return expanded;
        }
        
        public void setExpanded(boolean expanded)
        {
        	this.expanded=expanded;
        }
}
