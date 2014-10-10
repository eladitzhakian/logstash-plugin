/*
 * The MIT License
 *
 * Copyright 2014 Rusty Gerard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package jenkins.plugins.logstash.persistence;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * Abstract data access object for Logstash indexers.
 *
 * @author Rusty Gerard
 * @since 1.0.0
 */
abstract class AbstractLogstashIndexerDao implements LogstashIndexerDao {
  protected String host;
  protected int port;
  protected String key;
  protected String username;
  protected String password;

  void init(String host, int port, String key, String username, String password) {
    this.host = host;
    this.port = port;
    this.key = key;
    this.password = password;

    if (StringUtils.isBlank(host)) {
      throw new IllegalArgumentException("host name is required");
    }
  }

  // @Override
  public JSONObject buildPayload(BuildData buildData, String jenkinsUrl, List<String> logLines) {
    JSONObject payload = new JSONObject();
    payload.put("data", buildData.toJson());
    payload.put("message", logLines);
    payload.put("source", "jenkins");
    payload.put("source_host", jenkinsUrl);
    payload.put("@timestamp", buildData.getTimestamp());
    payload.put("version", 1);

    return payload;
  }

  // @Override
  public String getHost() {
    return host;
  }

  // @Override
  public int getPort() {
    return port;
  }
}
