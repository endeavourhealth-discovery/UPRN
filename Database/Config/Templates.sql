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
#identifier:start<tr>
<td>{{identifier.use}}</td>
<td>{{identifier.system}}</td>
<td>{{identifier.value}}</td>
</tr>#identifier:end
</tbody>
</table>
    {{no.such.resource.path}}
    #name:start<b>{{name.use}}: </b>{{name.text}}#name:end<br>
<b>DOB: </b>{{birthDate}}

#address:start
<ul>
<li><b>Use: </b>{{address.use}}</li>
<li><b>Lines:</b>
  <ul>
  #address.line:start
  <li>{{address.line.}}</li>
  #address.line:end
</ul>
</li>
</ul>
#address:end'
    );
