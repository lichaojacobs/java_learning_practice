package com.jacobs.hbase;

import com.jacobs.hbase.factory.TableFactory;
import com.jacobs.hbase.util.HBaseUtil;

import org.apache.hadoop.conf.Configuration;

import java.nio.charset.Charset;

import lombok.Data;

/**
 * Created by lichao on 2017/3/10.
 */
@Data
public abstract class HBaseAccessor {
  private String encoding;
  private Charset charset;
  private Configuration configuration;
  private TableFactory tableFactory;

  public void init() {
    if (this.configuration == null) {
      throw new RuntimeException("a valid configuration is required");
    } else {
      this.charset = HBaseUtil.getCharset(this.encoding);
    }
  }

  public HBaseAccessor() {
    this.charset = HBaseUtil.getCharset(this.encoding);
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof HBaseAccessor)) {
      return false;
    } else {
      HBaseAccessor other = (HBaseAccessor) o;
      if (!other.canEqual(this)) {
        return false;
      } else {
        label59:
        {
          String this$encoding = this.getEncoding();
          String other$encoding = other.getEncoding();
          if (this$encoding == null) {
            if (other$encoding == null) {
              break label59;
            }
          } else if (this$encoding.equals(other$encoding)) {
            break label59;
          }

          return false;
        }

        Charset this$charset = this.getCharset();
        Charset other$charset = other.getCharset();
        if (this$charset == null) {
          if (other$charset != null) {
            return false;
          }
        } else if (!this$charset.equals(other$charset)) {
          return false;
        }

        Configuration this$configuration = this.getConfiguration();
        Configuration other$configuration = other.getConfiguration();
        if (this$configuration == null) {
          if (other$configuration != null) {
            return false;
          }
        } else if (!this$configuration.equals(other$configuration)) {
          return false;
        }

        TableFactory this$tableFactory = this.getTableFactory();
        TableFactory other$tableFactory = other.getTableFactory();
        if (this$tableFactory == null) {
          if (other$tableFactory != null) {
            return false;
          }
        } else if (!this$tableFactory.equals(other$tableFactory)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof HBaseAccessor;
  }

  public int hashCode() {
    boolean PRIME = true;
    byte result = 1;
    String $encoding = this.getEncoding();
    int result1 = result * 59 + ($encoding == null ? 43 : $encoding.hashCode());
    Charset $charset = this.getCharset();
    result1 = result1 * 59 + ($charset == null ? 43 : $charset.hashCode());
    Configuration $configuration = this.getConfiguration();
    result1 = result1 * 59 + ($configuration == null ? 43 : $configuration.hashCode());
    TableFactory $tableFactory = this.getTableFactory();
    result1 = result1 * 59 + ($tableFactory == null ? 43 : $tableFactory.hashCode());
    return result1;
  }

  public String toString() {
    return "HBaseAccessor(encoding=" + this.getEncoding() + ", charset=" + this.getCharset() +
        ", configuration=" + this.getConfiguration() + ", tableFactory=" + this.getTableFactory() +
        ")";
  }
}
