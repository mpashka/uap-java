/**
 * Copyright 2012 Twitter, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua_parser;

import java.util.Map;

/**
 * Device parsed data class
 *
 * @author Steve Jiang (@sjiang) <gh at iamsteve com>
 */
public class Device {
  public final String device;
  public final String brand;
  public final String model;

  public Device(String device, String brand, String model) {
    this.device = device;
    this.brand = brand;
    this.model = model;
  }

  public static Device fromMap(Map<String, String> m) {
    return new Device(m.get("family"), m.get("brand"), m.get("model"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Device device1 = (Device) o;

    if (device != null ? !device.equals(device1.device) : device1.device != null) return false;
    if (brand != null ? !brand.equals(device1.brand) : device1.brand != null) return false;
    if (model != null ? !model.equals(device1.model) : device1.model != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = device != null ? device.hashCode() : 0;
    result = 31 * result + (brand != null ? brand.hashCode() : 0);
    result = 31 * result + (model != null ? model.hashCode() : 0);
    return result;
  }


  @Override
  public String toString() {
    return "Device{" +
            "device='" + device + '\'' +
            ", brand='" + brand + '\'' +
            ", model='" + model + '\'' +
            '}';
  }
}