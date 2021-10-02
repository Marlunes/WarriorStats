package com.cockerstats.cockerstats

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cockerstats.cockerstats.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private var maxID: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Matches-${getDateTitle()}")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxID = dataSnapshot.childrenCount + 1
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        database.addValueEventListener(postListener)

        bindViews()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun bindViews() {

        binding.btnMeron.setOnClickListener(actionListener)
        binding.btnWala.setOnClickListener(actionListener)
        binding.btnDraw.setOnClickListener(actionListener)

        binding.rbMeronBettingDehado.setOnClickListener(bettingListener)
        binding.rbMeronBettingDehado.setOnClickListener(bettingListener)

        binding.rbWalaBettingDehado.setOnClickListener(bettingListener)
        binding.rbWalaBettingLlamado.setOnClickListener(bettingListener)

    }

    private fun getColorName(selected: Int?): Color? {

        return when (selected) {
            null -> null

            R.id.rbMeronColorDarkRed -> Color.DARK_RED
            R.id.rbWalaColorDarkRed -> Color.DARK_RED

            R.id.rbMeronColorLightRed -> Color.LIGHT_RED
            R.id.rbWalaColorLightRed -> Color.LIGHT_RED

            R.id.rbMeronColorGold -> Color.GOLD
            R.id.rbWalaColorGold -> Color.GOLD

            R.id.rbMeronColorWhite -> Color.WHITE
            R.id.rbWalaColorWhite -> Color.WHITE

            R.id.rbMeronColorHennie -> Color.HENNIE
            R.id.rbWalaColorHennie -> Color.HENNIE

            R.id.rbMeronColorDome -> Color.DOME
            R.id.rbWalaColorDome -> Color.DOME

            R.id.rbMeronColorHatchGray -> Color.HATCH_GRAY
            R.id.rbWalaColorHatchGray -> Color.HATCH_GRAY

            R.id.rbMeronColorBlueMug -> Color.BLUE_MUG
            R.id.rbWalaColorBlueMug -> Color.BLUE_MUG

            R.id.rbMeronColorBlackMug -> Color.BLACK_MUG
            R.id.rbWalaColorBlackMug -> Color.BLACK_MUG

            else -> null
        }

    }

    private fun getLegName(selected: Int?): Leg? {

        return when (selected) {
            null -> null

            R.id.rbMeronLegDark -> Leg.DARK
            R.id.rbWalaLegDark -> Leg.DARK

            R.id.rbMeronLegWhite -> Leg.WHITE
            R.id.rbWalaLegWhite -> Leg.WHITE

            R.id.rbMeronLegYellow -> Leg.YELLOW
            R.id.rbWalaLegYellow -> Leg.YELLOW

            R.id.rbMeronLegRed -> Leg.RED
            R.id.rbWalaLegRed -> Leg.RED

            else -> null
        }

    }

    private fun getTailName(selected: Int?): Tail? {

        return when (selected) {
            null -> null

            R.id.rbMeronTailBlack -> Tail.BLACK
            R.id.rbWalaTailBlack -> Tail.BLACK

            R.id.rbMeronTailWhite -> Tail.WHITE
            R.id.rbWalaTailWhite -> Tail.WHITE

            R.id.rbMeronTailWithBlack -> Tail.WHITE_BLACK
            R.id.rbWalaTailWithBlack -> Tail.WHITE_BLACK

            R.id.rbMeronTailWithWhite -> Tail.BLACK_WHITE
            R.id.rbWalaTailWithWhite -> Tail.BLACK_WHITE

            R.id.rbMeronTailMix -> Tail.MIX
            R.id.rbWalaTailMix -> Tail.MIX

            else -> null
        }

    }

    private fun getBettingName(selected: Int?): Betting? {

        return when (selected) {
            null -> null

            R.id.rbMeronBettingDehado -> Betting.DEHADO
            R.id.rbWalaBettingDehado -> Betting.DEHADO

            R.id.rbMeronBettingLlamado -> Betting.LLAMADO
            R.id.rbWalaBettingLlamado -> Betting.LLAMADO

            else -> null
        }

    }

    private fun getCombValue(selected: Int?): Boolean {

        return when (selected) {
            null -> false
            else -> true
        }

    }

    private fun clear() {
        binding.rbgWalaColor.clearCheck()
        binding.rbgWalaLeg.clearCheck()
        binding.rbgWalaTail.clearCheck()
        binding.rbgWalaBetting.clearCheck()
        binding.rbgWalaComb.clearCheck()

        binding.rbgMeronColor.clearCheck()
        binding.rbgMeronLeg.clearCheck()
        binding.rbgMeronTail.clearCheck()
        binding.rbgMeronBetting.clearCheck()
        binding.rbgMeronComb.clearCheck()
    }

    private val actionListener = View.OnClickListener { view ->

        val meronColor = getColorName(binding.rbgMeronColor.checkedRadioButtonId)
        val meronLeg = getLegName(binding.rbgMeronLeg.checkedRadioButtonId)
        val meronTail = getTailName(binding.rbgMeronTail.checkedRadioButtonId)
        val meronBetting = getBettingName(binding.rbgMeronBetting.checkedRadioButtonId)
        val meronComb = getCombValue(binding.rbgMeronComb.checkedRadioButtonId)

        val walaColor = getColorName(binding.rbgWalaColor.checkedRadioButtonId)
        val walaLeg = getLegName(binding.rbgWalaLeg.checkedRadioButtonId)
        val walaTail = getTailName(binding.rbgWalaTail.checkedRadioButtonId)
        val walaBetting = getBettingName(binding.rbgWalaBetting.checkedRadioButtonId)
        val walaComb = getCombValue(binding.rbgWalaComb.checkedRadioButtonId)

        val meron = CockAttributes(meronColor, meronLeg, meronTail, meronBetting, meronComb)
        val wala = CockAttributes(walaColor, walaLeg, walaTail, walaBetting, walaComb)
        var match = Match(meron, wala, ResultSide.MERON, getDateFull())

        var sideText = "WALA"

        when (view.id) {
            R.id.btnMeron -> {
                match = Match(meron, wala, ResultSide.MERON, getDateFull())
                sideText = "MERON"
            }
            R.id.btnWala -> {
                match = Match(meron, wala, ResultSide.WALA, getDateFull())
                sideText = "WALA"
            }
            R.id.btnDraw -> {
                match = Match(meron, wala, ResultSide.DRAW, getDateFull())
                sideText = "DRAW"
            }
        }

        val alertDialog = AlertDialog.Builder(this)

        alertDialog.apply {
            setTitle("WINNER IS $sideText")
            setMessage("Are you sure winner is $sideText?")
            setPositiveButton("YES I AM SURE") { _, _ ->
                database.child(maxID.toString()).setValue(match).addOnSuccessListener {
                    clear()
                    Toast.makeText(applicationContext,"Success Request",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(applicationContext,"Error Request",Toast.LENGTH_LONG).show()
                }
            }
            setNegativeButton("CANCEL") { _, _ ->

            }
        }.create().show()

    }

    private val bettingListener = View.OnClickListener { view ->

        when (view.id) {
            R.id.rbMeronBettingDehado -> {
                binding.rbgWalaBetting.check(binding.rbWalaBettingLlamado.id)
            }
            R.id.rbMeronBettingLlamado -> {
                binding.rbgWalaBetting.check(binding.rbWalaBettingDehado.id)
            }
            R.id.rbWalaBettingDehado -> {
                binding.rbgMeronBetting.check(binding.rbMeronBettingLlamado.id)
            }
            R.id.rbWalaBettingLlamado -> {
                binding.rbgMeronBetting.check(binding.rbMeronBettingDehado.id)
            }
        }

    }

    private fun getDateFull(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val currentDate = dateFormat.format(Date())

        return currentDate.toString()
    }

    private fun getDateTitle(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = dateFormat.format(Date())

        return currentDate.toString()
    }

}