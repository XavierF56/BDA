<barsStevenSeagal>
{
	for $a in doc("XMLalcool")//alcool[prix < 5]
	return
		for $c in doc("SQLclient")/res/tuple
		where $c/alcool_prefere = $a/@id
		return $c
}
</barsStevenSeagal>
