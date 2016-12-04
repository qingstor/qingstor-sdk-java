// +-------------------------------------------------------------------------
// | Copyright (C) 2016 Yunify, Inc.
// +-------------------------------------------------------------------------
// | Licensed under the Apache License, Version 2.0 (the "License");
// | you may not use this work except in compliance with the License.
// | You may obtain a copy of the License in the LICENSE file, or at:
// |
// | http://www.apache.org/licenses/LICENSE-2.0
// |
// | Unless required by applicable law or agreed to in writing, software
// | distributed under the License is distributed on an "AS IS" BASIS,
// | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// | See the License for the specific language governing permissions and
// | limitations under the License.
// +-------------------------------------------------------------------------

var fs   = require('fs');
var path = require('path');
var glob = require('glob');

var _         = require('lodash');
var ZSchema   = require('z-schema');
var JSONRefs = require('json-refs');

var validator = new ZSchema();

const swagger2Paths  = glob.sync(`${__dirname}/**/swagger/api_v2.0.json`);
const swagger2Schema = fs.readFileSync(`${__dirname}/api_spec_schema_swagger_v2.0.json`);

describe('API Spec (Swagger v2.0)', () => {
  _.each(swagger2Paths, spec => {
    it(`${spec} should be valid.`, done => {
      JSONRefs.resolveRefs(JSON.parse(fs.readFileSync(spec)), {
        filter: ['relative'],
        relativeBase: path.dirname(spec),
      }).then(
        result => {
          const isValid = validator.validate(result.resolved, JSON.parse(swagger2Schema));
          if (!isValid) {
            done(`\n${JSON.stringify(validator.getLastErrors(), null, 2)}`);
          } else {
            done();
          }
        },
        error => {
          done(error);
        }
      );
    });
  });
});
