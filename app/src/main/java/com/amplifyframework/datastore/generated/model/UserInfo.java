package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the UserInfo type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserInfos", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class UserInfo implements Model {
  public static final QueryField ID = field("UserInfo", "id");
  public static final QueryField USER = field("UserInfo", "user");
  public static final QueryField CONTENT = field("UserInfo", "content");
  public static final QueryField USERNAME = field("UserInfo", "username");
  public static final QueryField PASSWORD = field("UserInfo", "password");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String user;
  private final @ModelField(targetType="String") String content;
  private final @ModelField(targetType="String") String username;
  private final @ModelField(targetType="String") String password;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUser() {
      return user;
  }
  
  public String getContent() {
      return content;
  }
  
  public String getUsername() {
      return username;
  }
  
  public String getPassword() {
      return password;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private UserInfo(String id, String user, String content, String username, String password) {
    this.id = id;
    this.user = user;
    this.content = content;
    this.username = username;
    this.password = password;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserInfo userInfo = (UserInfo) obj;
      return ObjectsCompat.equals(getId(), userInfo.getId()) &&
              ObjectsCompat.equals(getUser(), userInfo.getUser()) &&
              ObjectsCompat.equals(getContent(), userInfo.getContent()) &&
              ObjectsCompat.equals(getUsername(), userInfo.getUsername()) &&
              ObjectsCompat.equals(getPassword(), userInfo.getPassword()) &&
              ObjectsCompat.equals(getCreatedAt(), userInfo.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userInfo.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getContent())
      .append(getUsername())
      .append(getPassword())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserInfo {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("password=" + String.valueOf(getPassword()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static UserInfo justId(String id) {
    return new UserInfo(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user,
      content,
      username,
      password);
  }
  public interface BuildStep {
    UserInfo build();
    BuildStep id(String id);
    BuildStep user(String user);
    BuildStep content(String content);
    BuildStep username(String username);
    BuildStep password(String password);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String user;
    private String content;
    private String username;
    private String password;
    @Override
     public UserInfo build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserInfo(
          id,
          user,
          content,
          username,
          password);
    }
    
    @Override
     public BuildStep user(String user) {
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep content(String content) {
        this.content = content;
        return this;
    }
    
    @Override
     public BuildStep username(String username) {
        this.username = username;
        return this;
    }
    
    @Override
     public BuildStep password(String password) {
        this.password = password;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String user, String content, String username, String password) {
      super.id(id);
      super.user(user)
        .content(content)
        .username(username)
        .password(password);
    }
    
    @Override
     public CopyOfBuilder user(String user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder password(String password) {
      return (CopyOfBuilder) super.password(password);
    }
  }
  
}
