package com.jacobs.elasticserach;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by lichao on 2017/2/17.
 */

@ConfigurationProperties(
    prefix = "elasticsearch"
)
public class ElasticsearchProperties {
  private String clusterName = "elasticsearch";
  private List<String> nodes;
  private boolean sniff = false;
  private boolean ignoreClusterName = false;
  private String pingTimeout = "5s";
  private String nodesSamplerInterval = "5s";

  public ElasticsearchProperties() {
  }

  public String getClusterName() {
    return this.clusterName;
  }

  public List<String> getNodes() {
    return this.nodes;
  }

  public boolean isSniff() {
    return this.sniff;
  }

  public boolean isIgnoreClusterName() {
    return this.ignoreClusterName;
  }

  public String getPingTimeout() {
    return this.pingTimeout;
  }

  public String getNodesSamplerInterval() {
    return this.nodesSamplerInterval;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public void setNodes(List<String> nodes) {
    this.nodes = nodes;
  }

  public void setSniff(boolean sniff) {
    this.sniff = sniff;
  }

  public void setIgnoreClusterName(boolean ignoreClusterName) {
    this.ignoreClusterName = ignoreClusterName;
  }

  public void setPingTimeout(String pingTimeout) {
    this.pingTimeout = pingTimeout;
  }

  public void setNodesSamplerInterval(String nodesSamplerInterval) {
    this.nodesSamplerInterval = nodesSamplerInterval;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof ElasticsearchProperties)) {
      return false;
    } else {
      ElasticsearchProperties other = (ElasticsearchProperties) o;
      if (!other.canEqual(this)) {
        return false;
      } else {
        String this$clusterName = this.getClusterName();
        String other$clusterName = other.getClusterName();
        if (this$clusterName == null) {
          if (other$clusterName != null) {
            return false;
          }
        } else if (!this$clusterName.equals(other$clusterName)) {
          return false;
        }

        List this$nodes = this.getNodes();
        List other$nodes = other.getNodes();
        if (this$nodes == null) {
          if (other$nodes != null) {
            return false;
          }
        } else if (!this$nodes.equals(other$nodes)) {
          return false;
        }

        if (this.isSniff() != other.isSniff()) {
          return false;
        } else if (this.isIgnoreClusterName() != other.isIgnoreClusterName()) {
          return false;
        } else {
          String this$pingTimeout = this.getPingTimeout();
          String other$pingTimeout = other.getPingTimeout();
          if (this$pingTimeout == null) {
            if (other$pingTimeout != null) {
              return false;
            }
          } else if (!this$pingTimeout.equals(other$pingTimeout)) {
            return false;
          }

          String this$nodesSamplerInterval = this.getNodesSamplerInterval();
          String other$nodesSamplerInterval = other.getNodesSamplerInterval();
          if (this$nodesSamplerInterval == null) {
            if (other$nodesSamplerInterval != null) {
              return false;
            }
          } else if (!this$nodesSamplerInterval.equals(other$nodesSamplerInterval)) {
            return false;
          }

          return true;
        }
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof ElasticsearchProperties;
  }

  public int hashCode() {
    boolean PRIME = true;
    byte result = 1;
    String $clusterName = this.getClusterName();
    int result1 = result * 59 + ($clusterName == null ? 43 : $clusterName.hashCode());
    List $nodes = this.getNodes();
    result1 = result1 * 59 + ($nodes == null ? 43 : $nodes.hashCode());
    result1 = result1 * 59 + (this.isSniff() ? 79 : 97);
    result1 = result1 * 59 + (this.isIgnoreClusterName() ? 79 : 97);
    String $pingTimeout = this.getPingTimeout();
    result1 = result1 * 59 + ($pingTimeout == null ? 43 : $pingTimeout.hashCode());
    String $nodesSamplerInterval = this.getNodesSamplerInterval();
    result1 =
        result1 * 59 + ($nodesSamplerInterval == null ? 43 : $nodesSamplerInterval.hashCode());
    return result1;
  }

  public String toString() {
    return "ElasticsearchProperties(clusterName=" + this.getClusterName() + ", nodes=" +
        this.getNodes() + ", sniff=" + this.isSniff() + ", ignoreClusterName=" +
        this.isIgnoreClusterName() + ", pingTimeout=" + this.getPingTimeout() +
        ", nodesSamplerInterval=" + this.getNodesSamplerInterval() + ")";
  }
}
