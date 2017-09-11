/*
 * Copyright 2017 Open Web IT B.V. (https://www.openweb.nl/)
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
package nl.openweb.hippo;

import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import nl.openweb.hippo.exception.SetupTeardownException;
import nl.openweb.jcr.Importer;
import nl.openweb.jcr.InMemoryJcrRepository;

/**
 * @author Ebrahim Aharpour
 * @since 8/27/2017
 */
public abstract class BaseHippoTest extends AbstractHippoTest {

    private InMemoryJcrRepository repository;

    @Override
    protected Importer getImporter() {
        try {
            repository = new InMemoryJcrRepository();
            return new Importer.Builder(() -> {
                Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
                return session.getRootNode();
            })
                    .addUnknownTypes(true)
                    .saveSession(true)
                    .addUuid(true)
                    .build();
        } catch (Exception e) {
            throw new SetupTeardownException(e);
        }
    }

    @Override
    public void teardown() {
        super.teardown();
        repository.close();
    }
}
