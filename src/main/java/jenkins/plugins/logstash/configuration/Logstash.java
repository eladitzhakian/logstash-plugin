package jenkins.plugins.logstash.configuration;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import jenkins.plugins.logstash.persistence.LogstashDao;

public class Logstash extends HostBasedLogstashIndexer<LogstashDao>
{

  @DataBoundConstructor
  public Logstash()
  {
  }

  @Override
  protected LogstashDao createIndexerInstance()
  {
    return new LogstashDao(getHost(), getPort());
  }

  @Extension
  @Symbol("logstash")
  public static class Descriptor extends LogstashIndexerDescriptor
  {

    @Override
    public String getDisplayName()
    {
      return "Logstash UDP";
    }

    @Override
    public int getDefaultPort()
    {
      return 1666;
    }

  }
}
