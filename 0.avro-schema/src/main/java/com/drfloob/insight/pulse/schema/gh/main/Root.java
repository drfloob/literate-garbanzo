/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.drfloob.insight.pulse.schema.gh.main;

import com.drfloob.insight.pulse.schema.gh.main.root.Actor;
import com.drfloob.insight.pulse.schema.gh.main.root.Org;
import com.drfloob.insight.pulse.schema.gh.main.root.Repo;
import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Root extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5496324546690848010L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Root\",\"namespace\":\"com.drfloob.insight.pulse.schema\",\"fields\":[{\"name\":\"type\",\"type\":[\"string\",\"null\"]},{\"name\":\"public\",\"type\":[\"boolean\",\"null\"]},{\"name\":\"payload\",\"type\":[\"string\",\"null\"]},{\"name\":\"repo\",\"type\":[{\"type\":\"record\",\"name\":\"Repo\",\"namespace\":\"com.drfloob.insight.pulse.schema.root\",\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"name\",\"type\":[\"string\",\"null\"]},{\"name\":\"url\",\"type\":[\"string\",\"null\"]}]},\"null\"]},{\"name\":\"actor\",\"type\":[{\"type\":\"record\",\"name\":\"Actor\",\"namespace\":\"com.drfloob.insight.pulse.schema.root\",\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"login\",\"type\":[\"string\",\"null\"]},{\"name\":\"gravatar_id\",\"type\":[\"string\",\"null\"]},{\"name\":\"avatar_url\",\"type\":[\"string\",\"null\"]},{\"name\":\"url\",\"type\":[\"string\",\"null\"]}]},\"null\"]},{\"name\":\"org\",\"type\":[{\"type\":\"record\",\"name\":\"Org\",\"namespace\":\"com.drfloob.insight.pulse.schema.root\",\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"login\",\"type\":[\"string\",\"null\"]},{\"name\":\"gravatar_id\",\"type\":[\"string\",\"null\"]},{\"name\":\"avatar_url\",\"type\":[\"string\",\"null\"]},{\"name\":\"url\",\"type\":[\"string\",\"null\"]}]},\"null\"]},{\"name\":\"created_at\",\"type\":[\"long\",\"null\"]},{\"name\":\"id\",\"type\":[\"string\",\"null\"]},{\"name\":\"other\",\"type\":[\"string\",\"null\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence type;
  @Deprecated public java.lang.Boolean public$;
  @Deprecated public java.lang.CharSequence payload;
  @Deprecated public Repo repo;
  @Deprecated public Actor actor;
  @Deprecated public Org org;
  @Deprecated public java.lang.Long created_at;
  @Deprecated public java.lang.CharSequence id;
  @Deprecated public java.lang.CharSequence other;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Root() {}

  /**
   * All-args constructor.
   * @param type The new value for type
   * @param public$ The new value for public
   * @param payload The new value for payload
   * @param repo The new value for repo
   * @param actor The new value for actor
   * @param org The new value for org
   * @param created_at The new value for created_at
   * @param id The new value for id
   * @param other The new value for other
   */
  public Root(java.lang.CharSequence type, java.lang.Boolean public$, java.lang.CharSequence payload, Repo repo, Actor actor, Org org, java.lang.Long created_at, java.lang.CharSequence id, java.lang.CharSequence other) {
    this.type = type;
    this.public$ = public$;
    this.payload = payload;
    this.repo = repo;
    this.actor = actor;
    this.org = org;
    this.created_at = created_at;
    this.id = id;
    this.other = other;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return type;
    case 1: return public$;
    case 2: return payload;
    case 3: return repo;
    case 4: return actor;
    case 5: return org;
    case 6: return created_at;
    case 7: return id;
    case 8: return other;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: type = (java.lang.CharSequence)value$; break;
    case 1: public$ = (java.lang.Boolean)value$; break;
    case 2: payload = (java.lang.CharSequence)value$; break;
    case 3: repo = (Repo)value$; break;
    case 4: actor = (Actor)value$; break;
    case 5: org = (Org)value$; break;
    case 6: created_at = (java.lang.Long)value$; break;
    case 7: id = (java.lang.CharSequence)value$; break;
    case 8: other = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.CharSequence getType() {
    return type;
  }

  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.CharSequence value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'public$' field.
   * @return The value of the 'public$' field.
   */
  public java.lang.Boolean getPublic$() {
    return public$;
  }

  /**
   * Sets the value of the 'public$' field.
   * @param value the value to set.
   */
  public void setPublic$(java.lang.Boolean value) {
    this.public$ = value;
  }

  /**
   * Gets the value of the 'payload' field.
   * @return The value of the 'payload' field.
   */
  public java.lang.CharSequence getPayload() {
    return payload;
  }

  /**
   * Sets the value of the 'payload' field.
   * @param value the value to set.
   */
  public void setPayload(java.lang.CharSequence value) {
    this.payload = value;
  }

  /**
   * Gets the value of the 'repo' field.
   * @return The value of the 'repo' field.
   */
  public Repo getRepo() {
    return repo;
  }

  /**
   * Sets the value of the 'repo' field.
   * @param value the value to set.
   */
  public void setRepo(Repo value) {
    this.repo = value;
  }

  /**
   * Gets the value of the 'actor' field.
   * @return The value of the 'actor' field.
   */
  public Actor getActor() {
    return actor;
  }

  /**
   * Sets the value of the 'actor' field.
   * @param value the value to set.
   */
  public void setActor(Actor value) {
    this.actor = value;
  }

  /**
   * Gets the value of the 'org' field.
   * @return The value of the 'org' field.
   */
  public Org getOrg() {
    return org;
  }

  /**
   * Sets the value of the 'org' field.
   * @param value the value to set.
   */
  public void setOrg(Org value) {
    this.org = value;
  }

  /**
   * Gets the value of the 'created_at' field.
   * @return The value of the 'created_at' field.
   */
  public java.lang.Long getCreatedAt() {
    return created_at;
  }

  /**
   * Sets the value of the 'created_at' field.
   * @param value the value to set.
   */
  public void setCreatedAt(java.lang.Long value) {
    this.created_at = value;
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.CharSequence getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.CharSequence value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'other' field.
   * @return The value of the 'other' field.
   */
  public java.lang.CharSequence getOther() {
    return other;
  }

  /**
   * Sets the value of the 'other' field.
   * @param value the value to set.
   */
  public void setOther(java.lang.CharSequence value) {
    this.other = value;
  }

  /**
   * Creates a new Root RecordBuilder.
   * @return A new Root RecordBuilder
   */
  public static Root.Builder newBuilder() {
    return new Root.Builder();
  }

  /**
   * Creates a new Root RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Root RecordBuilder
   */
  public static Root.Builder newBuilder(Root.Builder other) {
    return new Root.Builder(other);
  }

  /**
   * Creates a new Root RecordBuilder by copying an existing Root instance.
   * @param other The existing instance to copy.
   * @return A new Root RecordBuilder
   */
  public static Root.Builder newBuilder(Root other) {
    return new Root.Builder(other);
  }

  /**
   * RecordBuilder for Root instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Root>
    implements org.apache.avro.data.RecordBuilder<Root> {

    private java.lang.CharSequence type;
    private java.lang.Boolean public$;
    private java.lang.CharSequence payload;
    private Repo repo;
    private Repo.Builder repoBuilder;
    private Actor actor;
    private Actor.Builder actorBuilder;
    private Org org;
    private Org.Builder orgBuilder;
    private java.lang.Long created_at;
    private java.lang.CharSequence id;
    private java.lang.CharSequence other;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Root.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.type)) {
        this.type = data().deepCopy(fields()[0].schema(), other.type);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.public$)) {
        this.public$ = data().deepCopy(fields()[1].schema(), other.public$);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.payload)) {
        this.payload = data().deepCopy(fields()[2].schema(), other.payload);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.repo)) {
        this.repo = data().deepCopy(fields()[3].schema(), other.repo);
        fieldSetFlags()[3] = true;
      }
      if (other.hasRepoBuilder()) {
        this.repoBuilder = Repo.newBuilder(other.getRepoBuilder());
      }
      if (isValidValue(fields()[4], other.actor)) {
        this.actor = data().deepCopy(fields()[4].schema(), other.actor);
        fieldSetFlags()[4] = true;
      }
      if (other.hasActorBuilder()) {
        this.actorBuilder = Actor.newBuilder(other.getActorBuilder());
      }
      if (isValidValue(fields()[5], other.org)) {
        this.org = data().deepCopy(fields()[5].schema(), other.org);
        fieldSetFlags()[5] = true;
      }
      if (other.hasOrgBuilder()) {
        this.orgBuilder = Org.newBuilder(other.getOrgBuilder());
      }
      if (isValidValue(fields()[6], other.created_at)) {
        this.created_at = data().deepCopy(fields()[6].schema(), other.created_at);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.id)) {
        this.id = data().deepCopy(fields()[7].schema(), other.id);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.other)) {
        this.other = data().deepCopy(fields()[8].schema(), other.other);
        fieldSetFlags()[8] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Root instance
     * @param other The existing instance to copy.
     */
    private Builder(Root other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.type)) {
        this.type = data().deepCopy(fields()[0].schema(), other.type);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.public$)) {
        this.public$ = data().deepCopy(fields()[1].schema(), other.public$);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.payload)) {
        this.payload = data().deepCopy(fields()[2].schema(), other.payload);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.repo)) {
        this.repo = data().deepCopy(fields()[3].schema(), other.repo);
        fieldSetFlags()[3] = true;
      }
      this.repoBuilder = null;
      if (isValidValue(fields()[4], other.actor)) {
        this.actor = data().deepCopy(fields()[4].schema(), other.actor);
        fieldSetFlags()[4] = true;
      }
      this.actorBuilder = null;
      if (isValidValue(fields()[5], other.org)) {
        this.org = data().deepCopy(fields()[5].schema(), other.org);
        fieldSetFlags()[5] = true;
      }
      this.orgBuilder = null;
      if (isValidValue(fields()[6], other.created_at)) {
        this.created_at = data().deepCopy(fields()[6].schema(), other.created_at);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.id)) {
        this.id = data().deepCopy(fields()[7].schema(), other.id);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.other)) {
        this.other = data().deepCopy(fields()[8].schema(), other.other);
        fieldSetFlags()[8] = true;
      }
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.CharSequence getType() {
      return type;
    }

    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public Root.Builder setType(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.type = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public Root.Builder clearType() {
      type = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'public$' field.
      * @return The value.
      */
    public java.lang.Boolean getPublic$() {
      return public$;
    }

    /**
      * Sets the value of the 'public$' field.
      * @param value The value of 'public$'.
      * @return This builder.
      */
    public Root.Builder setPublic$(java.lang.Boolean value) {
      validate(fields()[1], value);
      this.public$ = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'public$' field has been set.
      * @return True if the 'public$' field has been set, false otherwise.
      */
    public boolean hasPublic$() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'public$' field.
      * @return This builder.
      */
    public Root.Builder clearPublic$() {
      public$ = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'payload' field.
      * @return The value.
      */
    public java.lang.CharSequence getPayload() {
      return payload;
    }

    /**
      * Sets the value of the 'payload' field.
      * @param value The value of 'payload'.
      * @return This builder.
      */
    public Root.Builder setPayload(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.payload = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'payload' field has been set.
      * @return True if the 'payload' field has been set, false otherwise.
      */
    public boolean hasPayload() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'payload' field.
      * @return This builder.
      */
    public Root.Builder clearPayload() {
      payload = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'repo' field.
      * @return The value.
      */
    public Repo getRepo() {
      return repo;
    }

    /**
      * Sets the value of the 'repo' field.
      * @param value The value of 'repo'.
      * @return This builder.
      */
    public Root.Builder setRepo(Repo value) {
      validate(fields()[3], value);
      this.repoBuilder = null;
      this.repo = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'repo' field has been set.
      * @return True if the 'repo' field has been set, false otherwise.
      */
    public boolean hasRepo() {
      return fieldSetFlags()[3];
    }

    /**
     * Gets the Builder instance for the 'repo' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public Repo.Builder getRepoBuilder() {
      if (repoBuilder == null) {
        if (hasRepo()) {
          setRepoBuilder(Repo.newBuilder(repo));
        } else {
          setRepoBuilder(Repo.newBuilder());
        }
      }
      return repoBuilder;
    }

    /**
     * Sets the Builder instance for the 'repo' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public Root.Builder setRepoBuilder(Repo.Builder value) {
      clearRepo();
      repoBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'repo' field has an active Builder instance
     * @return True if the 'repo' field has an active Builder instance
     */
    public boolean hasRepoBuilder() {
      return repoBuilder != null;
    }

    /**
      * Clears the value of the 'repo' field.
      * @return This builder.
      */
    public Root.Builder clearRepo() {
      repo = null;
      repoBuilder = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'actor' field.
      * @return The value.
      */
    public Actor getActor() {
      return actor;
    }

    /**
      * Sets the value of the 'actor' field.
      * @param value The value of 'actor'.
      * @return This builder.
      */
    public Root.Builder setActor(Actor value) {
      validate(fields()[4], value);
      this.actorBuilder = null;
      this.actor = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'actor' field has been set.
      * @return True if the 'actor' field has been set, false otherwise.
      */
    public boolean hasActor() {
      return fieldSetFlags()[4];
    }

    /**
     * Gets the Builder instance for the 'actor' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public Actor.Builder getActorBuilder() {
      if (actorBuilder == null) {
        if (hasActor()) {
          setActorBuilder(Actor.newBuilder(actor));
        } else {
          setActorBuilder(Actor.newBuilder());
        }
      }
      return actorBuilder;
    }

    /**
     * Sets the Builder instance for the 'actor' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public Root.Builder setActorBuilder(Actor.Builder value) {
      clearActor();
      actorBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'actor' field has an active Builder instance
     * @return True if the 'actor' field has an active Builder instance
     */
    public boolean hasActorBuilder() {
      return actorBuilder != null;
    }

    /**
      * Clears the value of the 'actor' field.
      * @return This builder.
      */
    public Root.Builder clearActor() {
      actor = null;
      actorBuilder = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'org' field.
      * @return The value.
      */
    public Org getOrg() {
      return org;
    }

    /**
      * Sets the value of the 'org' field.
      * @param value The value of 'org'.
      * @return This builder.
      */
    public Root.Builder setOrg(Org value) {
      validate(fields()[5], value);
      this.orgBuilder = null;
      this.org = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'org' field has been set.
      * @return True if the 'org' field has been set, false otherwise.
      */
    public boolean hasOrg() {
      return fieldSetFlags()[5];
    }

    /**
     * Gets the Builder instance for the 'org' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public Org.Builder getOrgBuilder() {
      if (orgBuilder == null) {
        if (hasOrg()) {
          setOrgBuilder(Org.newBuilder(org));
        } else {
          setOrgBuilder(Org.newBuilder());
        }
      }
      return orgBuilder;
    }

    /**
     * Sets the Builder instance for the 'org' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public Root.Builder setOrgBuilder(Org.Builder value) {
      clearOrg();
      orgBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'org' field has an active Builder instance
     * @return True if the 'org' field has an active Builder instance
     */
    public boolean hasOrgBuilder() {
      return orgBuilder != null;
    }

    /**
      * Clears the value of the 'org' field.
      * @return This builder.
      */
    public Root.Builder clearOrg() {
      org = null;
      orgBuilder = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'created_at' field.
      * @return The value.
      */
    public java.lang.Long getCreatedAt() {
      return created_at;
    }

    /**
      * Sets the value of the 'created_at' field.
      * @param value The value of 'created_at'.
      * @return This builder.
      */
    public Root.Builder setCreatedAt(java.lang.Long value) {
      validate(fields()[6], value);
      this.created_at = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'created_at' field has been set.
      * @return True if the 'created_at' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'created_at' field.
      * @return This builder.
      */
    public Root.Builder clearCreatedAt() {
      created_at = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.CharSequence getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public Root.Builder setId(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.id = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public Root.Builder clearId() {
      id = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'other' field.
      * @return The value.
      */
    public java.lang.CharSequence getOther() {
      return other;
    }

    /**
      * Sets the value of the 'other' field.
      * @param value The value of 'other'.
      * @return This builder.
      */
    public Root.Builder setOther(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.other = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'other' field has been set.
      * @return True if the 'other' field has been set, false otherwise.
      */
    public boolean hasOther() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'other' field.
      * @return This builder.
      */
    public Root.Builder clearOther() {
      other = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    public Root build() {
      try {
        Root record = new Root();
        record.type = fieldSetFlags()[0] ? this.type : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.public$ = fieldSetFlags()[1] ? this.public$ : (java.lang.Boolean) defaultValue(fields()[1]);
        record.payload = fieldSetFlags()[2] ? this.payload : (java.lang.CharSequence) defaultValue(fields()[2]);
        if (repoBuilder != null) {
          record.repo = this.repoBuilder.build();
        } else {
          record.repo = fieldSetFlags()[3] ? this.repo : (Repo) defaultValue(fields()[3]);
        }
        if (actorBuilder != null) {
          record.actor = this.actorBuilder.build();
        } else {
          record.actor = fieldSetFlags()[4] ? this.actor : (Actor) defaultValue(fields()[4]);
        }
        if (orgBuilder != null) {
          record.org = this.orgBuilder.build();
        } else {
          record.org = fieldSetFlags()[5] ? this.org : (Org) defaultValue(fields()[5]);
        }
        record.created_at = fieldSetFlags()[6] ? this.created_at : (java.lang.Long) defaultValue(fields()[6]);
        record.id = fieldSetFlags()[7] ? this.id : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.other = fieldSetFlags()[8] ? this.other : (java.lang.CharSequence) defaultValue(fields()[8]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}