/*
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.zeebe.exporter.kafka.configuration;

import io.zeebe.exporter.record.Record;
import java.util.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

public class ExporterConfiguration {
  private static final String CLIENT_ID_FORMAT = "zb-kafka-exporter-%s";

  private List<String> servers = Collections.singletonList("localhost:9092");
  private int maxInFlightRecords = 1_000;
  private ProducerConfiguration producer = new ProducerConfiguration();

  private String topic;

  public Producer<Record, Record> newProducer(String exporterId) {
    final Properties properties = producer.newProperties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", servers));
    properties.put(ProducerConfig.CLIENT_ID_CONFIG, String.format(CLIENT_ID_FORMAT, exporterId));

    return new KafkaProducer<>(properties);
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public int getMaxInFlightRecords() {
    return maxInFlightRecords;
  }

  public void setMaxInFlightRecords(int maxInFlightRecords) {
    this.maxInFlightRecords = maxInFlightRecords;
  }

  public List<String> getServers() {
    return servers;
  }

  public void setServers(List<String> servers) {
    this.servers = servers;
  }

  public ProducerConfiguration getProducerConfiguration() {
    return producer;
  }

  public void getProducerConfiguration(ProducerConfiguration producerConfiguration) {
    this.producer = producerConfiguration;
  }

  @Override
  public String toString() {
    return "ExporterConfiguration{"
        + "servers="
        + servers
        + ", maxInFlightRecords="
        + maxInFlightRecords
        + ", producer="
        + producer
        + ", topic='"
        + topic
        + '\''
        + '}';
  }
}
