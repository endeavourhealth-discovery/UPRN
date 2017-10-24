insert into config (app_id, config_id, config_data)
    values (
        'eds-data-validation',
      'Template-Patient',
      '
    <b>Gender: </b>{{gender}}<br>
    <b>Managing Org: </b>{{managingOrganization.reference}}<br>

    <table>
    <thead>
    <th>Use</th><th>System</th><th>Value</th>
</thead>
<tbody>
<tr>
<td>{{identifier.use}}</td>
<td>{{identifier.system}}</td>
<td>{{identifier.value}}</td>
</tr>
</tbody>
</table>
    {{no.such.resource.path}}
    {{name.use}}: </b>{{name.text}}<br>
<b>DOB: </b>{{birthDate}}

<ul>
<li><b>Use: </b>{{address.use}}</li>
<li><b>Lines:</b>
  <ul>
  <li>{{address.line}}</li>
</ul>
</li>
</ul>
');
