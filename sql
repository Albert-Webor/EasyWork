select distinct mt.btch_grp_num
                        FROM
                            dmb_i_kdpt_feed_mt_stmt mt
	                    join dma_dp_dpsit_acct sa
	                    on sa.lgl_pern_code = mt.lgl_pern_code
	                    and sa.cust_acct_num = mt.cust_acct_num
	                    and sa.ccy_code_num = mt.ccy_code_num
	                    and sa.prod_grp_code = #prod_grp_code#]]></str>
            <where type="Where">
                <and type="And">
                    <str type="Str"><![CDATA[mt.lgl_pern_code = #lgl_pern_code#]]></str>
                    <str type="Str"><![CDATA[mt.txn_dt_8 = #txn_dt_8#]]></str>
                    <str type="Str"><![CDATA[mt.stus = #spec_deal_flg#]]></str>
                    <str type="Str"><![CDATA[mt.${mt_flag} = #spec_deal_flg#]]></str>
                    <if test="sub_acct_ste != null &amp;&amp; sub_acct_ste != &quot;&quot;" type="If">
                        <str type="Str">
                            <![CDATA[(sa.sub_acct_ste = #BaseEnumType.E_SUB_ACCT_STE.NORMAL# OR (sa.sub_acct_ste = #sub_acct_ste# and sa.cncl_acct_dt = #txn_dt_8#))]]>
                        </str>
                    </if>
                    <if test="bank_code == null || bank_code == &quot;&quot;" type="If">
                        <str type="Str">
                            <![CDATA[mt.bank_code = #lgl_pern_code# and mt.ccy_code_num = #ccy_code_num#]]></str>
                    </if>
                    <if test="bank_code != null &amp;&amp; bank_code != &quot;&quot;" type="If">
                        <str type="Str">
                            <![CDATA[((mt.bank_code = #lgl_pern_code# AND mt.ccy_code_num != #ccy_code_num# ) OR mt.bank_code != #lgl_pern_code# )]]></str>
                    </if>
                </and>
            </where>